package com.easub.bi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Constant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4706151151115433851L;

	
	/**
	 * 视频
	 */
	public static class Videos{
		/**
		 * 视频源类型：上传 0
		 */
		public final static int SOURCE_TYPE_UPLOAD = 0;
		/**
		 * 视频源类型：拉取（youtube） 1
		 */
		public final static int SOURCE_TYPE_PULL = 1;
		/**
		 * 视频源类型：剪辑 2
		 */
		public final static int SOURCE_TYPE_CLIP = 2;
		/**
		 * 视频源类型：直播 3
		 */
		public final static int SOURCE_TYPE_LIVE_BROADCAST = 3;
		/**
		 * 视频源类型：直播剪辑 4
		 */
		public final static int SOURCE_TYPE_LIVE_BROADCAST_CLIP = 4;
		/**
		 * 视频源类型：广告 5
		 */
		public final static int SOURCE_TYPE_AD = 5;
		/**
		 * 视频源类型：混剪（合集） 6
		 */
		public final static int SOURCE_TYPE_MIXING_CLIP = 6;
		/**
		 * 视频源类型：购买 7
		 */
		public final static int SOURCE_TYPE_BUY = 7;
		/**
		 * 视频源类型：普通图片 8
		 */
		public final static int SOURCE_TYPE_NORMAL_PICTURE = 8;
		/**
		 * 视频源类型：360图片 9
		 */
		public final static int SOURCE_TYPE_360_PICTURE = 9;
		/**
		 * 视频源类型：导播直播 10
		 */
		public final static int SOURCE_TYPE_DIRECT_LIVE_BROADCAST = 10;
		/**
		 * 视频源类型：私库共享 11
		 */
		public final static int SOURCE_TYPE_PRIVATE_LIBRARY_SHARE = 11;
		/**
		 * 视频源类型：公共库共享 12
		 */
		public final static int SOURCE_TYPE_PUBLIC_LIBRARY_SHARE = 12;
		/**
		 * 视频源类型：微博接入 13
		 */
		public final static int SOURCE_TYPE_WEIBO_JOIN = 14;
		/**
		 * 视频源类型： 剪辑工具 使用 本地上传的视频 是不是可以在云媒资 显示出来 15
		 * 作用已失效不用
		 * 
		 */
		public final static int SOURCE_TYPE_15 = 15;
		
		public static Map<Integer,String> sourceTypeMap = new HashMap<Integer, String>();
		
		static {
			sourceTypeMap.put(SOURCE_TYPE_UPLOAD, "上传");
			sourceTypeMap.put(SOURCE_TYPE_PULL, "拉取");
			sourceTypeMap.put(SOURCE_TYPE_CLIP, "剪辑");
			sourceTypeMap.put(SOURCE_TYPE_LIVE_BROADCAST, "直播");
			sourceTypeMap.put(SOURCE_TYPE_LIVE_BROADCAST_CLIP, "直播剪辑");
			sourceTypeMap.put(SOURCE_TYPE_MIXING_CLIP, "混剪");
			sourceTypeMap.put(SOURCE_TYPE_BUY, "购买");
			sourceTypeMap.put(SOURCE_TYPE_NORMAL_PICTURE, "普通图片");
			sourceTypeMap.put(SOURCE_TYPE_360_PICTURE, "360图片");
			sourceTypeMap.put(SOURCE_TYPE_DIRECT_LIVE_BROADCAST, "导播直播");
			sourceTypeMap.put(SOURCE_TYPE_PRIVATE_LIBRARY_SHARE, "私库共享");
			sourceTypeMap.put(SOURCE_TYPE_PUBLIC_LIBRARY_SHARE, "公共库共享");
			sourceTypeMap.put(SOURCE_TYPE_WEIBO_JOIN, "微博接入");
		}
		
	}
	
	
}
