package com.hsc.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hsc.googleplay.R;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/18.
 */

public abstract class LoadingPager extends FrameLayout{

    private static final int STATE_LOAD_UNDO = 1;// 未加载
    private static final int STATE_LOAD_LOADING = 2;// 正在加载
    private static final int STATE_LOAD_ERROR = 3;// 加载失败
    private static final int STATE_LOAD_EMPTY = 4;// 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO;//当前状态
    
    private View mLoadingpage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPager( Context context) {
        super(context);
        initView();
    }

    public LoadingPager( Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPager( Context context, AttributeSet attrs,  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //加载中的布局
        if (mLoadingpage == null) {
            mLoadingpage = UIUtils.inflate(R.layout.pager_loading);
            //Log.i("info", "mLoadingpage: ");
            addView(mLoadingpage);//将加载的布局添加到当前的帧布局
        }
        //加载失败的布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.pager_erro);
            //Log.i("info", "mErrorPage: ");
            addView(mErrorPage);
        }
        
        //加载数据为空的布局
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            //Log.i("info", "mEmptyPage: ");
            addView(mEmptyPage);
        }
        showRightPage();
    }

    private void showRightPage() {
        mLoadingpage.setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE
                : View.GONE);

        mErrorPage.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        mEmptyPage.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);

        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();

            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }
    
    public void loadDate(){
        //没有加载就开始加载
        if (mCurrentState != STATE_LOAD_LOADING) {
            mCurrentState = STATE_LOAD_LOADING;
            
            new Thread(){
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    
                    //运行在主线程
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();//网络加载结束后，更新网络状态

                                //根据最新状态来刷新页面
                                showRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }
    
    //加载成功后显示的布局，必须由调用者来实现
    public abstract View onCreateSuccessView();
    
    //加载网络数据  返回值表示请求网络结束后的状态
    public abstract ResultState onLoad();

    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS), STATE_EMPTY(STATE_LOAD_EMPTY), STATE_ERROR(STATE_LOAD_ERROR);
        
        private int state;
        ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
