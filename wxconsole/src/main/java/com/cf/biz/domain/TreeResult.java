package com.cf.biz.domain;

import java.util.ArrayList;
import java.util.List;

public class TreeResult {
	private String id;
	private String text;
	private List<TreeResult> children = new ArrayList<TreeResult>();
	private boolean leaf;
	private boolean expanded;
	
	public TreeResult(){}
	public TreeResult(String id,String text,boolean leaf,boolean expanded){
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.expanded = expanded;
	}
	public TreeResult(String id,String text,List<TreeResult> children,boolean leaf,boolean expanded){
		new TreeResult(id,text,leaf,expanded);
		this.children = children;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeResult> getChildren() {
		return children;
	}
	public void setChildren(List<TreeResult> children) {
		this.children = children;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
}
