package test.baway.com.erjiliebiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView mExpandableListView;
    ExpandableListViewAdapter mExpandableListViewAdapter;
    private static final String TAG = "Main";
    private List<String> list = new ArrayList<>();
    private List<f_classify_right01.DatasBean.ClassListBean> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        String url = "http://169.254.96.27/mobile/index.php?act=goods_class&gc_id=1";

                OkHttpUtils
                        .get()
                        .url(url)
                        .build()
                        .execute(new StringCallback()
                        {
                            @Override
                            public void onError(Request request, Exception e)
                            {

                            }

                            @Override
                            public void onResponse(String response)
                            {
                                f_classify_right01 data = new Gson().fromJson(response,f_classify_right01.class);
                                list2.addAll(data.getDatas().getClass_list());
                                for (int i = 0;i<list2.size();i++)
                                {
                                    list.add(list2.get(i).getGc_name());
                                }
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        final String[][] arr = new String[list2.size()][];

                                        for (int i = 0;i<list2.size();i++){
                                            String url = "http://169.254.96.27/mobile/index.php?act=goods_class&gc_id="+list2.get(i).getGc_id();
                                            String aa = xutils.getUrlConnect(url);
                                            f_classify_right02 data2 = new Gson().fromJson(aa,f_classify_right02.class);
                                            List<f_classify_right02.DatasBean.ClassListBean> list3 = new ArrayList<f_classify_right02.DatasBean.ClassListBean>();
                                            list3.addAll(data2.getDatas().getClass_list());
                                            String[] arr2 = new String[list3.size()];
                                            for (int a=0;a<list3.size();a++){
                                                arr2[a] = list3.get(a).getGc_name();
                                            }
                                            arr[i]=arr2;
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mExpandableListViewAdapter = new ExpandableListViewAdapter(MainActivity.this,list,arr);
                                                mExpandableListView.setAdapter(mExpandableListViewAdapter);   //设置它的adapter
                                                for (int i = 0 ;i<list.size() ;i++){
                                                    mExpandableListView.expandGroup(i);
                                                }
                                            }
                                        });
                                    }
                                }.start();
                            }
                        });

        //设置父item的点击事件
        mExpandableListView
                .setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent,
                                                View v, int groupPosition, long id) {
                        return false;
                    }
                });

        //设置子item的点击事件
        mExpandableListView
                .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent,
                                                View v, int groupPosition, int childPosition,
                                                long id) {
                        Log.e(TAG, "groupPosition=" + groupPosition
                                + ",childPosition=" + childPosition);
                        return false;
                    }
                });

    }



}
