package com.xw.supercar.shiro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.StoppedSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.util.CollectionUtils;

public class ShiroSimpleSession implements ValidatingSession, Serializable {

   private static final long serialVersionUID = -7125642695178165650L;

   protected static final long MILLIS_PER_SECOND = 1000;
   protected static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
   protected static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

   static int bitIndexCounter = 0;
   private static final int ID_BIT_MASK = 1 << bitIndexCounter++;
   private static final int START_TIMESTAMP_BIT_MASK = 1 << bitIndexCounter++;
   private static final int STOP_TIMESTAMP_BIT_MASK = 1 << bitIndexCounter++;
   private static final int LAST_ACCESS_TIME_BIT_MASK = 1 << bitIndexCounter++;
   private static final int TIMEOUT_BIT_MASK = 1 << bitIndexCounter++;
   private static final int EXPIRED_BIT_MASK = 1 << bitIndexCounter++;
   private static final int HOST_BIT_MASK = 1 << bitIndexCounter++;
   private static final int ATTRIBUTES_BIT_MASK = 1 << bitIndexCounter++;
   private static final int USERNAME_BIT_MASK = 1 << bitIndexCounter++;
   private static final int USER_AGENT_BIT_MASK = 1 << bitIndexCounter++;
   private static final int REMOTE_IP_BIT_MASK = 1 << bitIndexCounter++;
   private static final int IS_HIDDEN_BIT_MASK = 1 << bitIndexCounter++;
   private static final int IS_FORCE_LOGOUT_BIT_MASK = 1 << bitIndexCounter++;
   private static final int LAST_UPDATE_TIME_BIT_MASK = 1 << bitIndexCounter++;

   private transient Serializable id;
   private transient Date startTimestamp;
   private transient Date stopTimestamp;
   private transient Date lastAccessTime;
   private transient long timeout;
   private transient boolean expired;
   private transient String host;
   private transient Map<Object, Object> attributes;
   private transient String username;
   private transient String userAgent;
   private transient String remoteIp;
   private transient Boolean isHidden;
   private transient Boolean isForceLogout;
   private transient Date lastUpdateTime;


   public ShiroSimpleSession() {
       this.timeout = DefaultSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT;
       this.startTimestamp = new Date();
       this.lastAccessTime = this.startTimestamp;
       this.lastUpdateTime = this.startTimestamp;
   }

   public ShiroSimpleSession(String host) {
       this();
       this.host = host;
   }

   public Serializable getId() {
       return this.id;
   }

   public void setId(Serializable id) {
       this.id = id;
   }

   public Date getStartTimestamp() {
       return startTimestamp;
   }

   public void setStartTimestamp(Date startTimestamp) {
       this.startTimestamp = startTimestamp;
   }

   public Date getStopTimestamp() {
       return stopTimestamp;
   }

   public void setStopTimestamp(Date stopTimestamp) {
       this.stopTimestamp = stopTimestamp;
   }

   public Date getLastAccessTime() {
       return lastAccessTime;
   }

   public void setLastAccessTime(Date lastAccessTime) {
       this.lastAccessTime = lastAccessTime;
   }

   public boolean isExpired() {
       return expired;
   }

   public void setExpired(boolean expired) {
       this.expired = expired;
       this.lastUpdateTime = new Date();
   }

   public long getTimeout() {
       return timeout;
   }

   public void setTimeout(long timeout) {
       this.timeout = timeout;
   }

   public String getHost() {
       return host;
   }

   public void setHost(String host) {
       this.host = host;
   }

   public Map<Object, Object> getAttributes() {
       return attributes;
   }

   public void setAttributes(Map<Object, Object> attributes) {
       this.attributes = attributes;
       this.lastUpdateTime = new Date();
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getUserAgent() {
       return userAgent;
   }

   public void setUserAgent(String userAgent) {
       this.userAgent = userAgent;
   }

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Boolean getIsForceLogout() {
		return isForceLogout;
	}

	public void setIsForceLogout(Boolean isForceLogout) {
		this.isForceLogout = isForceLogout;
		this.lastUpdateTime = new Date();
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

   public void touch() {
       this.lastAccessTime = new Date();
   }

   public void stop() {
       if (this.stopTimestamp == null) {
           this.stopTimestamp = new Date();
           this.lastUpdateTime = this.stopTimestamp;
       }
   }

   protected boolean isStopped() {
       return getStopTimestamp() != null;
   }

   protected void expire() {
       stop();
       this.expired = true;
   }

   /**
    * @since 0.9
    */
   public boolean isValid() {
       return !isStopped() && !isExpired();
   }

   /**
    * Determines if this session is expired.
    *
    * @return true if the specified session has expired, false otherwise.
    */
   protected boolean isTimedOut() {

       if (isExpired()) {
           return true;
       }

       long timeout = getTimeout();

       if (timeout >= 0l) {

           Date lastAccessTime = getLastAccessTime();

           if (lastAccessTime == null) {
               String msg = "session.lastAccessTime for session with id [" +
                       getId() + "] is null.  This value must be set at " +
                       "least once, preferably at least upon instantiation.  Please check the " +
                       getClass().getName() + " implementation and ensure " +
                       "this value will be set (perhaps in the constructor?)";
               throw new IllegalStateException(msg);
           }

           long expireTimeMillis = System.currentTimeMillis() - timeout;
           Date expireTime = new Date(expireTimeMillis);
           return lastAccessTime.before(expireTime);
       } else {
           /*if (log.isTraceEnabled()) {
               log.trace("No timeout for session with id [" + getId() +
                       "].  Session is not considered expired.");
           }*/
       }

       return false;
   }

   public void validate() throws InvalidSessionException {
       //check for stopped:
       if (isStopped()) {
           //timestamp is set, so the session is considered stopped:
           String msg = "Session with id [" + getId() + "] has been " +
                   "explicitly stopped.  No further interaction under this session is " +
                   "allowed.";
           throw new StoppedSessionException(msg);
       }

       //check for expiration
       if (isTimedOut()) {
           expire();

           //throw an exception explaining details of why it expired:
           Date lastAccessTime = getLastAccessTime();
           long timeout = getTimeout();

           Serializable sessionId = getId();

           DateFormat df = DateFormat.getInstance();
           String msg = "Session with id [" + sessionId + "] has expired. " +
                   "Last access time: " + df.format(lastAccessTime) +
                   ".  Current time: " + df.format(new Date()) +
                   ".  Session timeout is set to " + timeout / MILLIS_PER_SECOND + " seconds (" +
                   timeout / MILLIS_PER_MINUTE + " minutes)";
           /*if (log.isTraceEnabled()) {
               log.trace(msg);
           }*/
           throw new ExpiredSessionException(msg);
       }
   }

   private Map<Object, Object> getAttributesLazy() {
       Map<Object, Object> attributes = getAttributes();
       if (attributes == null) {
           attributes = new HashMap<Object, Object>();
           setAttributes(attributes);
       }
       return attributes;
   }

   public Collection<Object> getAttributeKeys() throws InvalidSessionException {
       Map<Object, Object> attributes = getAttributes();
       if (attributes == null) {
           return Collections.emptySet();
       }
       return attributes.keySet();
   }

   public Object getAttribute(Object key) {
       Map<Object, Object> attributes = getAttributes();
       if (attributes == null) {
           return null;
       }
       return attributes.get(key);
   }

   public void setAttribute(Object key, Object value) {
       if (value == null) {
           removeAttribute(key);
       } else {
           getAttributesLazy().put(key, value);
           this.lastUpdateTime = new Date();
       }
   }

   public Object removeAttribute(Object key) {
       this.lastUpdateTime = new Date();
       Map<Object, Object> attributes = getAttributes();
       if (attributes == null) {
           return null;
       } else {
           return attributes.remove(key);
       }
   }

   @Override
   public boolean equals(Object obj) {
       if (this == obj) {
           return true;
       }
       if (obj instanceof ShiroSimpleSession) {
           ShiroSimpleSession other = (ShiroSimpleSession) obj;
           Serializable thisId = getId();
           Serializable otherId = other.getId();
           if (thisId != null && otherId != null) {
               return thisId.equals(otherId);
           } else {
               //fall back to an attribute based comparison:
               return onEquals(other);
           }
       }
       return false;
   }

   protected boolean onEquals(ShiroSimpleSession ss) {
       return (getStartTimestamp() != null ? getStartTimestamp().equals(ss.getStartTimestamp()) : ss.getStartTimestamp() == null) &&
               (getStopTimestamp() != null ? getStopTimestamp().equals(ss.getStopTimestamp()) : ss.getStopTimestamp() == null) &&
               (getLastAccessTime() != null ? getLastAccessTime().equals(ss.getLastAccessTime()) : ss.getLastAccessTime() == null) &&
               (getTimeout() == ss.getTimeout()) &&
               (isExpired() == ss.isExpired()) &&
               (getHost() != null ? getHost().equals(ss.getHost()) : ss.getHost() == null) &&
               (getAttributes() != null ? getAttributes().equals(ss.getAttributes()) : ss.getAttributes() == null) &&
               (getUsername() != null ? getUsername().equals(ss.getUsername()) : ss.getUsername() == null) &&
               (getUserAgent() != null ? getUserAgent().equals(ss.getUserAgent()) : ss.getUserAgent() == null) &&
               (getRemoteIp() != null ? getRemoteIp().equals(ss.getRemoteIp()) : ss.getRemoteIp() == null);
   }

   @Override
   public int hashCode() {
       Serializable id = getId();
       if (id != null) {
           return id.hashCode();
       }
       int hashCode = getStartTimestamp() != null ? getStartTimestamp().hashCode() : 0;
       hashCode = 31 * hashCode + (getStopTimestamp() != null ? getStopTimestamp().hashCode() : 0);
       hashCode = 31 * hashCode + (getLastAccessTime() != null ? getLastAccessTime().hashCode() : 0);
       hashCode = 31 * hashCode + Long.valueOf(Math.max(getTimeout(), 0)).hashCode();
       hashCode = 31 * hashCode + Boolean.valueOf(isExpired()).hashCode();
       hashCode = 31 * hashCode + (getHost() != null ? getHost().hashCode() : 0);
       hashCode = 31 * hashCode + (getAttributes() != null ? getAttributes().hashCode() : 0);
       hashCode = 31 * hashCode + (getUsername() != null ? getUsername().hashCode() : 0);
       hashCode = 31 * hashCode + (getUserAgent() != null ? getUserAgent().hashCode() : 0);
       hashCode = 31 * hashCode + (getRemoteIp() != null ? getRemoteIp().hashCode() : 0);
       return hashCode;
   }

   @Override
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(getClass().getName()).append(",id=").append(getId());
       return sb.toString();
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
       out.defaultWriteObject();
       short alteredFieldsBitMask = getAlteredFieldsBitMask();
       out.writeShort(alteredFieldsBitMask);
       if (id != null) {
           out.writeObject(id);
       }
       if (startTimestamp != null) {
           out.writeObject(startTimestamp);
       }
       if (stopTimestamp != null) {
           out.writeObject(stopTimestamp);
       }
       if (lastAccessTime != null) {
           out.writeObject(lastAccessTime);
       }
       if (timeout != 0l) {
           out.writeLong(timeout);
       }
       if (expired) {
           out.writeBoolean(expired);
       }
       if (host != null) {
           out.writeUTF(host);
       }
       if (!CollectionUtils.isEmpty(attributes)) {
           out.writeObject(attributes);
       }
       if (username != null) {
           out.writeUTF(username);
       }
       if (userAgent != null) {
           out.writeUTF(userAgent);
       }
       if (remoteIp != null) {
           out.writeUTF(remoteIp);
       }
       if (isHidden != null) {
           out.writeBoolean(isHidden);
       }
       if (isForceLogout != null) {
           out.writeBoolean(isForceLogout);
       }
       if (lastUpdateTime != null) {
           out.writeObject(lastUpdateTime);
       }
   }

   @SuppressWarnings({"unchecked"})
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
       in.defaultReadObject();
       short bitMask = in.readShort();

       if (isFieldPresent(bitMask, ID_BIT_MASK)) {
           this.id = (Serializable) in.readObject();
       }
       if (isFieldPresent(bitMask, START_TIMESTAMP_BIT_MASK)) {
           this.startTimestamp = (Date) in.readObject();
       }
       if (isFieldPresent(bitMask, STOP_TIMESTAMP_BIT_MASK)) {
           this.stopTimestamp = (Date) in.readObject();
       }
       if (isFieldPresent(bitMask, LAST_ACCESS_TIME_BIT_MASK)) {
           this.lastAccessTime = (Date) in.readObject();
       }
       if (isFieldPresent(bitMask, TIMEOUT_BIT_MASK)) {
           this.timeout = in.readLong();
       }
       if (isFieldPresent(bitMask, EXPIRED_BIT_MASK)) {
           this.expired = in.readBoolean();
       }
       if (isFieldPresent(bitMask, HOST_BIT_MASK)) {
           this.host = in.readUTF();
       }
       if (isFieldPresent(bitMask, ATTRIBUTES_BIT_MASK)) {
           this.attributes = (Map<Object, Object>) in.readObject();
       }
       if (isFieldPresent(bitMask, USERNAME_BIT_MASK)) {
           this.username = in.readUTF();
       }
       if (isFieldPresent(bitMask, USER_AGENT_BIT_MASK)) {
           this.userAgent = in.readUTF();
       }
       if (isFieldPresent(bitMask, REMOTE_IP_BIT_MASK)) {
           this.remoteIp = in.readUTF();
       }
       if (isFieldPresent(bitMask, IS_HIDDEN_BIT_MASK)) {
           this.isHidden = in.readBoolean();
       }
       if (isFieldPresent(bitMask, IS_FORCE_LOGOUT_BIT_MASK)) {
           this.isForceLogout = in.readBoolean();
       }
       if (isFieldPresent(bitMask, LAST_UPDATE_TIME_BIT_MASK)) {
           this.lastUpdateTime = (Date) in.readObject();
       }
   }

   private short getAlteredFieldsBitMask() {
       int bitMask = 0;
       bitMask = id != null ? bitMask | ID_BIT_MASK : bitMask;
       bitMask = startTimestamp != null ? bitMask | START_TIMESTAMP_BIT_MASK : bitMask;
       bitMask = stopTimestamp != null ? bitMask | STOP_TIMESTAMP_BIT_MASK : bitMask;
       bitMask = lastAccessTime != null ? bitMask | LAST_ACCESS_TIME_BIT_MASK : bitMask;
       bitMask = timeout != 0l ? bitMask | TIMEOUT_BIT_MASK : bitMask;
       bitMask = expired ? bitMask | EXPIRED_BIT_MASK : bitMask;
       bitMask = host != null ? bitMask | HOST_BIT_MASK : bitMask;
       bitMask = !CollectionUtils.isEmpty(attributes) ? bitMask | ATTRIBUTES_BIT_MASK : bitMask;
       bitMask = username != null ? bitMask | USERNAME_BIT_MASK : bitMask;
       bitMask = userAgent != null ? bitMask | USER_AGENT_BIT_MASK : bitMask;
       bitMask = remoteIp != null ? bitMask | REMOTE_IP_BIT_MASK : bitMask;
       bitMask = isHidden != null ? bitMask | IS_HIDDEN_BIT_MASK : bitMask;
       bitMask = isForceLogout != null ? bitMask | IS_FORCE_LOGOUT_BIT_MASK : bitMask;
       bitMask = lastUpdateTime != null ? bitMask | LAST_UPDATE_TIME_BIT_MASK : bitMask;
       return (short) bitMask;
   }

   private static boolean isFieldPresent(short bitMask, int fieldBitMask) {
       return (bitMask & fieldBitMask) != 0;
   }

}