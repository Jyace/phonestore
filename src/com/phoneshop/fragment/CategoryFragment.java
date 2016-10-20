package com.phoneshop.fragment;

import java.util.List;

import com.phoneshop.activity.CategoryResultActivity;
import com.phoneshop.activity.R;
import com.phoneshop.adapter.TreeViewAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * 分类的片段
 * @author 何
 *
 */
public class CategoryFragment extends Fragment {
	private View view;
	private ExpandableListView expandableListView;
	private TreeViewAdapter adapter;
	private List<TreeViewAdapter.TreeNode> treeNodes;	//所有列表节点
	private String[] groups;							//父类列表数据
	private String[][] childs;							//子类列表数据
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_category, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		expandableListView = (ExpandableListView) view.findViewById(R.id.expandablelistview);
		expandableListView.setGroupIndicator(null);
		adapter= new TreeViewAdapter(getActivity(), 40);
		
		getData();
		initData();
		adapter.updateTreeNode(treeNodes);
		expandableListView.setAdapter(adapter);
		//expandableListView.expandGroup(0);	//默认打开第一个父级
		listener();
	}

	/**类表item点击的监听*/
	private void listener() {
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent(getActivity(), CategoryResultActivity.class);
				intent.putExtra("categoryModel",  ""+adapter.getChild(groupPosition, childPosition));
				startActivity(intent);
				return false;
			}
		});
	}

	/**初始化自定适配器TreeViewAdapter中属性和子类的数据*/
	private void initData() {
		treeNodes = adapter.getTreeNode();
		for (int i = 0; i < groups.length; i++) {
			TreeViewAdapter.TreeNode node = new TreeViewAdapter.TreeNode();
			node.parent = groups[i];
			//System.out.println("groups[i]:"+groups[i]);
			for (int j = 0; j < childs[i].length; j++) {
				node.childs.add(childs[i][j]);
			}
			treeNodes.add(node);
		}
	}

	/** 获得数据，设置父级和自己的内容*/
	private void getData() {
		groups = new String[]{"按价格查看","按频幕尺寸查看","按手机系统查看"};
		childs = new String[][]{
				{"1000元以下","1000-2000元","2000-3000元","3000元以上"},
				{"小于4.5英寸","4.5-5.0英寸","大于5.0英寸"},
				{"android","ios"},
		};
		
	}
	
}
