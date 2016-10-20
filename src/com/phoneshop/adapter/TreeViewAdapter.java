package com.phoneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.phoneshop.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * ExpandableListView 的适配器
 * @author 何
 *
 */
public class TreeViewAdapter extends BaseExpandableListAdapter {
	private int myPaddingLeft = 0;
	
	/**自定义节点，包含本级（父级）及其包含的子级*/
	public static class TreeNode{
		public Object parent;
		public List<Object> childs = new ArrayList<Object>();
	}
	private List<TreeNode> treeNodes = new ArrayList<TreeViewAdapter.TreeNode>();
	private Context parentContext;
	
	public TreeViewAdapter(Context context,int myPaddingLeft){
		this.parentContext = context;
		this.myPaddingLeft = myPaddingLeft;
	}
	
	/**获得列表所有节点*/
	public List<TreeNode> getTreeNode(){
		return treeNodes;
	}
	
	/**更新列表所有节点*/
	public void updateTreeNode(List<TreeNode> nodes){
		treeNodes = nodes;
	}

	/**删除列表所有节点*/
	public void removeAllNodes(){
		treeNodes.clear();
	}
	
	/**自定义获得文本的方法，在获得子级视图中用*/
	public static TextView getTextView(Context context){
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams
				(ViewGroup.LayoutParams.MATCH_PARENT, 40);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		textView.setTextColor(Color.BLACK);
		return textView;
	}

	/**获得父级总数*/
	@Override
	public int getGroupCount() {
		return treeNodes.size();
	}

	/**获得子级总数*/
	@Override
	public int getChildrenCount(int groupPosition) {
		return treeNodes.get(groupPosition).childs.size();
	}

	/**获得父级内容*/
	@Override
	public Object getGroup(int groupPosition) {
		return treeNodes.get(groupPosition).parent;
	}

	/**获得子级内容*/
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return treeNodes.get(groupPosition).childs.get(childPosition);
	}

	/**获得父级索引*/
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**获得子级索引*/
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**获得父级列表视图*/
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.treeviewapater_group, null);//加载布局视图
		}
		//设置父级文本
		TextView groupContent = (TextView) view.findViewById(R.id.tv_groupContent);
		groupContent.setText(getGroup(groupPosition).toString());
		
		//设置父级图标
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_expandIcon);
		if (isExpanded) {
			imageView.setBackgroundResource(R.drawable.narrow_select);
		}else {
			imageView.setBackgroundResource(R.drawable.narrow);
		}
		return view;
	}

	/**获得子级列表视图*/
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		/*TextView textView = getTextView(this.parentContext);
		textView.setText(getChild(groupPosition, childPosition).toString());
		textView.setPadding(myPaddingLeft+40, 0, 0, 0);*/
		
		View view =convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view =  inflater.inflate(R.layout.treeviewapater_child, null, false);
		}
		TextView childContent = (TextView) view.findViewById(R.id.tv_childContent);
		childContent.setText(getChild(groupPosition, childPosition).toString());
		childContent.setPadding(myPaddingLeft+35, 0, 0, 0);
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	

}
