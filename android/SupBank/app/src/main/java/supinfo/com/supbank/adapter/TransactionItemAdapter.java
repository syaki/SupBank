package supinfo.com.supbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import supinfo.com.supbank.R;

/**交易列表项适配器
 * @Author long
 */
public class TransactionItemAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,Object>> listItems;
    private LayoutInflater listContainer;

    public final class ListItemView{
        public TextView deal_address;
        public TextView deal_type;
        public TextView deal_amount;
        public TextView deal_time;
        public TextView deal_status;
    }

    public TransactionItemAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listItems.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * ListView Item设置
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListItemView  listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.transaction_item,null);
            listItemView.deal_address = (TextView)convertView.findViewById(R.id.deal_address);
            listItemView.deal_amount = (TextView)convertView.findViewById(R.id.deal_amount);
            listItemView.deal_time= (TextView) convertView.findViewById(R.id.deal_time);
            listItemView.deal_type = (TextView) convertView.findViewById(R.id.deal_type);
            listItemView.deal_status = (TextView) convertView.findViewById(R.id.deal_status);
            convertView.setTag(listItemView);
        }else {
            listItemView = (ListItemView)convertView.getTag();
        }
        listItemView.deal_address.setText("地址: "+(String) listItems.get(position).get("address"));
        listItemView.deal_amount.setText("金额: "+(String) listItems.get(position).get("sum"));
        listItemView.deal_time.setText("时间: "+String.valueOf(listItems.get(position).get("timestamp")));
        listItemView.deal_type.setText("类型: "+String.valueOf(listItems.get(position).get("type")));
        listItemView.deal_status.setText("状态: "+String.valueOf(listItems.get(position).get("status")));
        return convertView;
    }

}
