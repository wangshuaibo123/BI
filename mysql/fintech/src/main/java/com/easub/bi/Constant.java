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
		/**
		 * 视频状态：上传中 0
		 */
		public static final int MEDIA_STATE_UPLOAD = 0;
		/**
		 * 视频状态：处理中 1
		 */
		public static final int MEDIA_STATE_HANDLE = 1;
		/**
		 * 视频状态：成功 2
		 */
		public static final int MEDIA_STATE_SUCCESS = 2;
		/**
		 * 视频状态：失败 3
		 */
		public static final int MEDIA_STATE_FAIL = 3;
		
		//分享
		//0转码中,1发送成功,2分享成功,3分享失败,5审核被拒绝,4审核中,6 等待分享
		/**
		 * 视频分享状态：转码中
		 */
		public static final int SHARE_STATE_TRANSCODING = 0;
		/**
		 * 视频分享状态：发送成功
		 */
		public static final int SHARE_STATE_SEND_SUCESS = 1;
		/**
		 * 视频分享状态：分享成功
		 */
		public static final int SHARE_STATE_SHARE_SUCESS = 2;
		/**
		 * 视频分享状态：分享失败
		 */
		public static final int SHARE_STATE_SHARE_FAIL = 3;
		/**
		 * 视频分享状态：审核中
		 */
		public static final int SHARE_STATE_AUDITING = 4;
		/**
		 * 视频分享状态：审核被拒绝
		 */
		public static final int SHARE_STATE_AUDIT_REFUSE = 5;
		/**
		 * 视频分享状态：等等分享
		 */
		public static final int SHARE_STATE_WAIT_SHARE = 6;
		
	}
	/**
	 * 素材
	 * @author jiangshuncheng
	 *
	 */
	public static class Materials{
		/**
		 * 上传
		 */
		public final static int SOURCE_TYPE_UPLOAD = 0;
		/**
		 * 生产剪辑
		 */
		public final static int SOURCE_TYPE_CLIP = 1;
		/**
		 * 购买
		 */
		public final static int SOURCE_TYPE_BUY = 2;
		/**
		 * 拉取
		 */
		public final static int SOURCE_TYPE_PULL = 3;
		
	}
	
	
}
