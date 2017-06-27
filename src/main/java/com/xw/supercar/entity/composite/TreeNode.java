package com.xw.supercar.entity.composite;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 树节点结构，用于组装树状结构json返回给前台
 *
 * @author wsz 2017-06-27
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class TreeNode {
	private String label;
	private List<TreeNode> children;
}
