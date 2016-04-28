package com.example.util;


import java.util.ArrayList;
import java.util.List;

import com.example.newonebuy.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**广告展示控件【图片滚动展示】
 * @author 张统强
 */
public class MyPosterView extends FrameLayout implements Runnable
{
	//点击的监听事件
	private MyPosterOnClick clickListener=null;
	private Context context;
	//滚动展示的控件
	private ViewPager viewPage=null;  
	//滚动展示控件的adapter
	private PageAdapter adapter=null;  
	//要展示的界面
	private List<View> views=null;  
	//展示图片的页码（一排点标志展示哪张图）
	private List<ImageView> imgs=null;
	//滚动时的事件监听
	private PageChangeListener listener=null;
	//“当前是哪页”的标志点布局
	private LinearLayout layoutShowPoint=null;
	//这个控件内的图片（drawable）集合
	private BitmapDrawable[] imgDrawable=null;
	//是否显示页码标志点
	private boolean isPointOut=true;
//	//是否不断的滚动
//	private boolean isScroll=true;
	//滚动线程休眠时间【秒】
	private int sleepTime=1;
	private int curPosition=0;//当前页码
	private int maxPage=0;//最大页码
	//自动滚动的线程 
	private Thread thread=null;
	private boolean isRun=true;//用于线程的运行
	
	public MyPosterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		// TODO Auto-generated constructor stub
		LayoutInflater inflater =LayoutInflater.from(context);
		inflater.inflate(R.layout.activity_show, this,true);
		listener=new PageChangeListener();
		viewPage = (ViewPager)findViewById(R.id.showPage);  
		 
		views = new ArrayList<View>();  
		layoutShowPoint=(LinearLayout)findViewById(R.id.showPointLayout);
//		((android.widget.RelativeLayout.LayoutParams) layoutShowPoint.getLayoutParams())
//		.setMargins(0,0,0,MyApplication.getAdapH(10));
		
		//实现一个PagerAdapter  
		adapter = new PageAdapter(views);  
		viewPage.setAdapter(adapter);  
		viewPage.setOnPageChangeListener(listener);
		this.setBackgroundColor(0xff8080ff);
	}
	/**
	 * 设置数据
	 */
	public void setData(BitmapDrawable[] _imgDrawable)
	{
		if(_imgDrawable==null)return;
		imgDrawable=_imgDrawable;
		ImageView imgView = null;  
		ImageView img=null;
		if(isPointOut)imgs = new ArrayList<ImageView>(); 
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 0, 5, 0);
		for(int i=0;i<imgDrawable.length; i++)
        {
        	if(isPointOut){
        		img=new ImageView(context);
        		img.setBackgroundResource((i==0)?
        				R.drawable.hot_white:R.drawable.hot_yellow);
        		img.setLayoutParams(params);
        		imgs.add(img);
        		layoutShowPoint.addView(img);
        	}
        	//设置ViewPager显示的页面内容   
        	imgView=new ImageView(context);  
        	imgView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        	imgView.setBackgroundDrawable(imgDrawable[i]);
        	final int position=i;
        	if(clickListener!=null){    
        		imgView.setOnClickListener(new OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				clickListener.onMyclick(position);
        			}
        		});
        	}
        	views.add(imgView);
        }
        viewPage.setCurrentItem(imgDrawable.length*100);
      
	}
	/**设置数据
	 * @param _imgDrawable
	 */
	public void setData(BitmapDrawable[] _imgDrawable,boolean isPointOut)
	{
		this.isPointOut=isPointOut;
		setData(_imgDrawable);
		if(!isPointOut&&layoutShowPoint!=null)
		{
			layoutShowPoint.setVisibility(LinearLayout.GONE);
		}
	}
	/**设置数据
	 * @param _imgDrawable
	 */
	public void setData(BitmapDrawable[] _imgDrawable,MyPosterOnClick _Listener,boolean isPointOut)
	{
		setMyOnClickListener(_Listener);
		this.isPointOut=isPointOut;
		setData(_imgDrawable);
		if(!isPointOut&&layoutShowPoint!=null)
		{
			layoutShowPoint.setVisibility(LinearLayout.GONE);
		}
	}
	/**设置数据
	 * @param _imgDrawable
	 */
	public void setData(BitmapDrawable[] _imgDrawable,MyPosterOnClick _Listener,boolean isPointOut,int time)
	{
		setData(_imgDrawable,_Listener,isPointOut);
		sleepTime=time;
		thread= new Thread(this);
		thread.start();
	}
	    
	/**
	 * 设置点击监听
	 */
	public void setMyOnClickListener(MyPosterOnClick _Listener)
	{
		this.clickListener=_Listener;
	}
	/**
	 * 向左滚动
	 */
	public void leftScroll()
	{
		if(viewPage!=null)
		viewPage.setCurrentItem((--curPosition<=0)?maxPage:curPosition);
	}
	/**
	 * 向右滚动
	 */
	public void rightScroll()
	{
		if(viewPage!=null)
		viewPage.setCurrentItem((++curPosition>=maxPage)?0:curPosition);
	}
	/**
	 * 设置当前页
	 */
	public void setCurrentPage(int position)
	{
		if(imgDrawable!=null)
		viewPage.setCurrentItem(position+(imgDrawable.length*100));
	}
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);			
			rightScroll();
		}
	};
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread thisThread = Thread.currentThread(); 
//        while (blinker == thisThread) { 
		while(isRun&&!Thread.interrupted()&&thisThread==thread)
		{
//			System.out.println("======线程========"+sleepTime);
			try {Thread.sleep(1000*sleepTime);} catch (InterruptedException e) 
			{
//				e.printStackTrace();
				if(thread!=null)thread.interrupt();
			}
			if(handler!=null)
				handler.sendMessage(Message.obtain(handler));
		}
		thisThread=null;
	} 
	
	
	/**
	 *清空数据，内存回收 
	 */
	public void clearMemory() 
	{
		// TODO Auto-generated method stub
		if(thread!=null)
		{
//			System.out.println("====>>>"+sleepTime);
			isRun=false;
			thread.interrupt();
			thread=null;
		}
		clickListener=null;
		context=null;
		//滚动展示的控件
		viewPage=null;  
		//滚动展示控件的adapter
		adapter=null;  
		//要展示的界面
		if(views!=null){
			views.clear();			
			views=null;  
		}
		//展示图片的页码（一排点标志展示哪张图）
		if(imgs!=null){
			imgs.clear();
			imgs=null;
		}
		//滚动时的事件监听
		listener=null;
		//“当前是哪页”的标志点布局
		layoutShowPoint=null;
		//这个控件内的图片（drawable）集合
		imgDrawable=null;
		handler=null;
	}
	   /**=========================内部类===>页面切换监听================================*/
    class PageChangeListener implements OnPageChangeListener
    {
		@Override
		public void onPageScrollStateChanged(int arg0) 
		{
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) 
		{
			
		}

		@Override
		public void onPageSelected(int position)
		{
			curPosition=position;
			if(isPointOut&&imgs!=null){				
				for(int i=0;i<imgs.size();i++)
				{
					//不是当前选中的page，其小圆点设置为未选中的状态
					imgs.get(i).setBackgroundResource((position%imgs.size() !=i)?
							R.drawable.hot_white:R.drawable.hot_yellow);
				}
			}
		}
    	
    }
    class PageAdapter extends PagerAdapter  
    {  
        List<View> views;  
          
        public PageAdapter( List<View> views)  
        {  
    	     this.views = views;  
        }   /**  
    	  * 要显示的页面的个数  
    	  */  
    	 @Override  
    	 public int getCount()  
    	 {  
    		//设置成最大值以便循环滑动
    		int cont=((views == null)? 0 : Integer.MAX_VALUE);
    		maxPage=cont;
    		return cont;
    	 }  
    	   
    	 /**  
    	  * 获取一个指定页面的title描述  
    	  * 如果返回null意味着这个页面没有标题，默认的实现就是返回null  
    	  * 如果要显示页面上的title则此方法必须实现  
    	  */  
    	 @Override  
    	 public CharSequence getPageTitle(int position)   
    	 {  
//    		 System.out.println("==标题==>"+titles[position]);
//    		 return titles[position];  
    		 return null;
    	 }  
    	   
    	 /**  
    	  *  创建指定position的页面。这个适配器会将页面加到容器container中。  
    	  * @param container 创建出的实例放到container中，这里的container就是viewPager  
    	  * @return 返回一个能表示该页面的对象，不一定要是view，可以其他容器或者页面。  
    	  */  
    	 @Override  
    	 public Object instantiateItem(ViewGroup container, int position)  
    	 {  
    		 try {
                 ((ViewPager) container).addView(views.get(position%views.size()), 0);
	         } catch (Exception e) {
	         }
    		 return (views.size()>0)?views.get(position%views.size()):null;
    	 }  
    	   
    	 /**  
    	  * 此方法会将容器中指定页面给移除  
    	  * 该方法中的参数container和position跟instantiateItem方法中的内容一致  
    	  * @param object 这个object 就是 instantiateItem方法中返回的那个Object  
    	  */  
    	 @Override  
    	 public void destroyItem(ViewGroup container, int position, Object object)  
    	 { 
    		 //由于需要它循环滚动，所以不能将其清除掉。
//    		 if(position<views.size())
//    		 {				 
//    			 container.removeView(views.get(position));  
//    		 }
    	 }  
    	   
    	 /**  
    	  * 这个方法就是比较一下容器中页面和instantiateItem方法返回的Object是不是同一个  
    	  * @param arg0 ViewPager中的一个页面  
    	  * @param arg1 instantiateItem方法返回的对象  
    	  */  
    	 @Override  
    	 public boolean isViewFromObject(View arg0, Object arg1)   
    	 {  
    		 return arg0 == arg1;  
    	 }  
       
          
    }
	 
	
	
	
//	@Override
//	public void onClick(View v) 
//	{
//		// TODO Auto-generated method stub
//		switch(v.getId())
//		{
//		case R.id.showNextPageBtn:
//			Intent intent = new Intent();
//	    	intent.setClass(this, HomeActivity.class);
//	    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	    	startActivity(intent);
//	    	finish();
//	    	intent=null;
//	    	clearMemory();
//			break;
//		}
//	}

//	@Override
//	public void clearMemory() {
//		// TODO Auto-generated method stub
//		
//	}
	
}















