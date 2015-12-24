package cn.flow.ryvonne.flowchartapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.flow.ryvonne.flowchartapp.R;
import cn.flow.ryvonne.flowchartapp.fragment.SingleFragment;

public class MainActivity extends AppCompatActivity {
    ListView mListView;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawer();
        initFragment();
    }

    void initDrawer() {//侧边栏
        List<MenuInfo> list = new ArrayList<MenuInfo>();
        list.add(new MenuInfo(1, "test"));
        mListView = (ListView) findViewById(R.id.left_drawer);
        mListView.setAdapter(new MyAdapter(list));
    }

    void initFragment() { //Fragment
        mViewPager = (ViewPager) findViewById(R.id.content_pager);
        SingleFragment singleFragment = new SingleFragment();

        mFragments.add(singleFragment);

        mViewPager.setAdapter(mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
    }

    class MenuInfo {
        public MenuInfo(int index, String title) {
            this.index = index;
            this.title = title;
        }

        int index;
        String title;
    }

    /**
     * 侧边栏显示适配器
     */
    class MyAdapter extends BaseAdapter {
        private List<MenuInfo> mlist;

        public MyAdapter(List<MenuInfo> mlist) {
            this.mlist = mlist;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_title, null);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTitle.setText(mlist.get(position).title);
            return convertView;
        }

        class ViewHolder {
            TextView tvTitle;
        }
    }
}
