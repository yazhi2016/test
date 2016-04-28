package com.example.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.homemodel.SignInSuccessByToken;
import com.example.homemodel.User;
import com.example.newonebuy.R;
import com.example.onebuy.LogInActivity;
import com.example.onebuy.PayActivity;
import com.example.util.Content;
import com.example.util.GlobalVariable;
import com.example.util.MyShop;
import com.example.util.MyShop_Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements OnClickListener {

    private View view;
    private ListView lv;
    private LvAdapter lvAdapter;
    LayoutInflater inflater;
    ArrayList<MyShop> list = new ArrayList<MyShop>();
    private TextView text_num_shopfra;
    private TextView text_num_shopfra2;
    int num = 0;
    String token;
    String uid;
    SharedPreferences sp;
    String score;
    String jsonStirng;

    private CheckBox checkbox_all;
    boolean isAllCheck;
    double allMoney;
    int choosedNum;
    private TextView text_nothing;

    public ShopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            this.inflater = inflater;
            view = inflater.inflate(R.layout.fragment_shop, container, false);
            text_nothing = (TextView) view.findViewById(R.id.text_nothing);
            text_num_shopfra = (TextView) view.findViewById(R.id.text_num_shopfra);
            text_num_shopfra2 = (TextView) view.findViewById(R.id.text_num_shopfra2);
            checkbox_all = (CheckBox) view.findViewById(R.id.checkbox_all);
            view.findViewById(R.id.button1).setOnClickListener(this);
            lv = (ListView) view.findViewById(R.id.lv_shopfra);
            lvAdapter = new LvAdapter();
            lv.setAdapter(lvAdapter);
            searchSql();

            checkbox_all.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchSql();
        sp = getActivity().getSharedPreferences("token", 0);
        token = sp.getString("token", "");
        uid = sp.getString("uid", "");
    }

    class LvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            TextView text_title_shopfra;
            TextView text_remaind_shopfra;
            TextView text_price_shopfra;
            CheckBox checkBox_shopfra;
            ImageView ima_shopfra;
            final EditText editText1;
            if (convertView == null) {
                view = inflater.inflate(R.layout.item_lv_shopfra, null);
                text_title_shopfra = (TextView) view.findViewById(R.id.text_title_shopfra);
                text_remaind_shopfra = (TextView) view.findViewById(R.id.text_remaind_shopfra);
                text_price_shopfra = (TextView) view.findViewById(R.id.text_price_shopfra);
                checkBox_shopfra = (CheckBox) view.findViewById(R.id.checkBox_shopfra);

                ima_shopfra = (ImageView) view.findViewById(R.id.ima_shopfra);
                editText1 = (EditText) view.findViewById(R.id.editText1);
                view.setTag(new MyTag(text_title_shopfra, text_remaind_shopfra, text_price_shopfra, checkBox_shopfra,
                        ima_shopfra, editText1));
            } else {
                view = convertView;
                MyTag tag = (MyTag) view.getTag();
                text_title_shopfra = tag.text_title_shopfra;
                text_remaind_shopfra = tag.text_remaind_shopfra;
                text_price_shopfra = tag.text_price_shopfra;
                checkBox_shopfra = tag.checkBox_shopfra;
                ima_shopfra = tag.ima_shopfra;
                editText1 = tag.editText1;
            }

            final MyShop shop = list.get(position);
            final String goodid = shop.getGoodid();
            checkBox_shopfra.setChecked(shop.getIsCheck());
            GlobalVariable.displayImageNoAnim(shop.getIma(), ima_shopfra);
            text_title_shopfra.setText("(第" + shop.getNum() + "期)" + shop.getTitle());
            text_remaind_shopfra.setText("剩余" + shop.getRemaind());
            text_price_shopfra.setText("￥" + shop.getPrice());
            editText1.setText(list.get(position).getBuynum());

            // 修改数量
            editText1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if ("".equals((s + ""))) { // 如果为空，数量设置为1
                        updateBuyNum(goodid, "1");
                    } else {
                        updateBuyNum(goodid, s + "");
                    }
                    calculateMoneyAndNum();
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // 选中状态改变
            checkBox_shopfra.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    updateChoosed(goodid, isChecked);
                    calculateMoneyAndNum();

                    // 判断是否全选
                    List<MyShop> dataList = new Select().from(MyShop.class).queryList();
                    for (int i = 0; i < dataList.size(); i++) {
                        boolean isCheck = dataList.get(i).getIsCheck();
                        if (!isCheck) { // 有一个没选中
                            isAllCheck = false;
                            checkbox_all.setChecked(false);
                            return;
                        }
                    }
                    // 全部都选中
                    isAllCheck = true;
                    checkbox_all.setChecked(true);
                }
            });

            // 删除购物车
            view.findViewById(R.id.ima_delete_shopfra).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    delData(goodid);
                    List<MyShop> dataList = new Select().from(MyShop.class).queryList();
                    list.clear();
                    list.addAll(dataList);
                    if(list.size() == 0) {
                        text_nothing.setVisibility(View.VISIBLE);
                    }
                    lvAdapter.notifyDataSetChanged();
                    calculateMoneyAndNum();
                }
            });

            // 数量减
            view.findViewById(R.id.ima_lose).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String string = editText1.getText().toString();
                    if (string.isEmpty()) {
                        string = "1";
                    }
                    int parseInt;
                    if (string.equals("1")) {
                        return;
                    } else {
                        parseInt = Integer.parseInt(string);
                        parseInt--;
                        editText1.setText(parseInt + "");
                    }
                    updateBuyNum(goodid, parseInt + "");
                    calculateMoneyAndNum();
                }
            });
            // 数量加
            view.findViewById(R.id.ima_add).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String string = editText1.getText().toString();
                    if (string.isEmpty()) {
                        string = "1";
                    }
                    int parseInt;
                    parseInt = Integer.parseInt(string);
                    parseInt++;
                    editText1.setText(parseInt + "");
                    updateBuyNum(goodid, parseInt + "");
                    calculateMoneyAndNum();
                }
            });
            return view;
        }

    }

    class MyTag {
        TextView text_title_shopfra;
        TextView text_remaind_shopfra;
        TextView text_price_shopfra;
        CheckBox checkBox_shopfra;
        ImageView ima_shopfra;
        EditText editText1;

        public MyTag(TextView text_title_shopfra, TextView text_remaind_shopfra, TextView text_price_shopfra,
                     CheckBox checkBox_shopfra, ImageView ima_shopfra, EditText editText1) {
            super();
            this.text_title_shopfra = text_title_shopfra;
            this.text_remaind_shopfra = text_remaind_shopfra;
            this.text_price_shopfra = text_price_shopfra;
            this.checkBox_shopfra = checkBox_shopfra;
            this.ima_shopfra = ima_shopfra;
            this.editText1 = editText1;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: // 点击结算
                if (token.isEmpty()) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LogInActivity.class);
                    intent.putExtra("state", "safe");
                    startActivity(intent);
                    return;
                } else {
                    ArrayList<MyShop> payList = new ArrayList<MyShop>();
                    List<MyShop> dataList = new Select().from(MyShop.class).queryList();
                    for (int i = 0; i < dataList.size(); i++) {
                        MyShop shop = dataList.get(i);
                        if (shop.getIsCheck()) {
                            payList.add(shop);
                        }
                    }
                    if (payList.size() == 0) {
                        Toast.makeText(getActivity(), "请选择商品!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(getActivity(), PayActivity.class));
                    }
//				signInByToken(); // 获得积分
                }
                break;
            case R.id.checkbox_all: // 点击全选按钮
                if (isAllCheck) { // 本来是全选，点击变全不选
                    List<MyShop> dataList = new Select().from(MyShop.class).queryList();
                    for (int i = 0; i < dataList.size(); i++) {
                        updateChoosed(dataList.get(i).getGoodid(), false);
                    }
                } else {
                    List<MyShop> dataList = new Select().from(MyShop.class).queryList();
                    for (int i = 0; i < dataList.size(); i++) {
                        updateChoosed(dataList.get(i).getGoodid(), true);
                    }
                }
                isAllCheck = !isAllCheck;
                calculateMoneyAndNum();
                lvAdapter.notifyDataSetChanged();

                break;

            default:
                break;
        }
    }

    public void signInByToken() {
        String url = Content.URL.SIGNINBYTOKEN + token + "&uid=" + uid;
        HTTPUtils.get(getActivity(), url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Spanned fromHtml = Html.fromHtml(arg0);
                SignInSuccessByToken parseJSON = GsonUtils.parseJSON(fromHtml + "", SignInSuccessByToken.class);
                User user2 = parseJSON.getUser();
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
    }

    class GoodInfo {
        String goodId;
        int num;
        Double singlePrice;

        public GoodInfo(String goodId, int num) {
            super();
            this.goodId = goodId;
            this.num = num;
        }
    }

    public void searchSql() {
        text_nothing.setVisibility(View.GONE);
        list.clear();
        List<MyShop> dataList = new Select().from(MyShop.class).queryList();
        list.addAll(dataList);
        if(list.size() == 0) {
            text_nothing.setVisibility(View.VISIBLE);
        }
        lvAdapter.notifyDataSetChanged();

        calculateMoneyAndNum();

        // 判断是否全选
        for (int i = 0; i < dataList.size(); i++) {
            boolean isCheck = dataList.get(i).getIsCheck();
            if (!isCheck) { // 有一个没选中
                isAllCheck = false;
                checkbox_all.setChecked(false);
                return;
            }
        }
        // 全部都选中
        isAllCheck = true;
        checkbox_all.setChecked(true);
        if (dataList.size() == 0) {
            isAllCheck = false;
            checkbox_all.setChecked(false);
        }
    }

    public void updateChoosed(String goodId, boolean ischeck) { // 更新是否选中
        MyShop shop = new Select().from(MyShop.class).where(MyShop_Table.goodid.eq(goodId)).querySingle();
        if (shop != null) {
            shop.isCheck = ischeck;
            // 存入数据库中
            shop.update();
        }
    }

    public void updateBuyNum(String goodId, String num) { // 更新购买数量和总价
        // 修改数据库中的内容
        MyShop newshop = new Select().from(MyShop.class).where(MyShop_Table.goodid.eq(goodId)).querySingle();
        if (newshop != null) {
            newshop.buynum = num;
            newshop.allprice = Integer.parseInt(num) * Double.parseDouble(newshop.price) + "";
            // 存入数据库中
            newshop.update();
        }
    }

    public void delData(String goodId) {
        new Delete().from(MyShop.class).where(MyShop_Table.goodid.eq(goodId)).query();
    }

    /**
     * 计算数据库中的选中个数和总价并在text上显示
     */
    private void calculateMoneyAndNum() {
        choosedNum = 0;
        allMoney = 0;
        List<MyShop> dataList = new Select().from(MyShop.class).queryList();
        for (int i = 0; i < dataList.size(); i++) {
            MyShop shop = dataList.get(i);
            if (shop.getIsCheck()) { // 选中状态
                choosedNum++;
                allMoney += Double.parseDouble(shop.getAllprice());
            }
        }
        text_num_shopfra.setText(choosedNum + "");
        text_num_shopfra2.setText(allMoney + "元");
    }
}
