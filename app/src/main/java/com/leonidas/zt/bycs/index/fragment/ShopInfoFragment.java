package com.leonidas.zt.bycs.index.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.library.SuperTextView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shop;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/2/28.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
@SuppressLint("ValidFragment")
public class ShopInfoFragment extends BaseFragment {
    private final String mShopId;
    private SuperTextView mShopInfo;
    private SuperTextView mOwner;
    private SuperTextView mPhone;
    private SuperTextView mAddress;
    private ImageView mPic;
    private LinearLayout mImgLayout;

    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance();

    public ShopInfoFragment(String id) {
        super();
        mShopId = id;
    }

    @Override
    public void initView(View view) {
        mAddress = (SuperTextView) view.findViewById(R.id.stv_shop_info_address);
        mShopInfo = (SuperTextView) view.findViewById(R.id.stv_shop_info_info);
        mPhone = (SuperTextView) view.findViewById(R.id.stv_shop_info_phone);
        mOwner = (SuperTextView) view.findViewById(R.id.stv_shop_info_owner);
        mPic = (ImageView) view.findViewById(R.id.img_shop_info_pic);
        mImgLayout = (LinearLayout) view.findViewById(R.id.layout_shop_info_images);
    }

    private void setInfo(Shop shop){
        if (shop != null){
            mAddress.setRightString(shop.getShopAddress());
            mShopInfo.setLeftBottomString(shop.getShopDesc());
            mPhone.setRightString(shop.getShopPhone());
            mOwner.setRightString(shop.getSellerName());
            GlideApp.with(mContext)
                    .load(Constant.API.images + shop.getShopPictures().get(0).getPicturePath())
                    .into(mPic);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_shop_info;
    }

    @Override
    public void initData() {
        getData();
    }

    private void getData(){
        StringBuffer params = new StringBuffer();
        params.append("?shopId=");
        params.append(mShopId);
        mOkHttpHelper.doGet(Constant.API.getShop + params, new BaseCallback<ResMessage<Data<Shop>>>() {


            @Override
            public void OnSuccess(Response response, ResMessage<Data<Shop>> dataResMessage) {
                Log.e("shop", "OnSuccess: " + dataResMessage.getData().getShop().getShopName() );
                setInfo(dataResMessage.getData().getShop());
            }

            @Override
            public void onError(Response response, int errCode, Exception e) {

            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {

            }
        });
    }
}
