package com.example.bod_members.members;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bod_members.R;

import java.util.ArrayList;
import java.util.List;


public class MemberListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Member> member_list;

    public MemberListAdapter(Context context, int layout, ArrayList<Member> foodsList) {
        this.context = context;
        this.layout = layout;
        this.member_list = foodsList;
    }

    @Override
    public int getCount() {
        return member_list.size();
    }

    @Override
    public Object getItem(int position) {
        return member_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void filterList(ArrayList<Member> filterList) {
        member_list=filterList;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        ImageView imageView_for_member;
        TextView text_name_for_member,text_year_for_member,text_roll_for_member,text_phone_for_member,text_email_for_member ;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.text_name_for_member = (TextView) row.findViewById(R.id.text_name_for_member);
            holder.text_year_for_member = (TextView) row.findViewById(R.id.text_year_for_member);
            holder.imageView_for_member = (ImageView) row.findViewById(R.id.image_food_for_member);
            holder.text_roll_for_member=(TextView)row.findViewById(R.id.text_roll_for_member);
            holder.text_phone_for_member=(TextView) row.findViewById(R.id.text_phone_for_member);
            holder.text_email_for_member=(TextView) row.findViewById(R.id.text_email_for_member);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Member member = member_list.get(position);

        holder.text_name_for_member.setText(member.getNaming());
        holder.text_year_for_member.setText(member.getYear());
        byte[] foodImage = member.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.text_roll_for_member.setText(member.getRoll_no());
        holder.imageView_for_member.setImageBitmap(bitmap);
        holder.text_phone_for_member.setText(member.getPhone());
        holder.text_email_for_member.setText(member.getEmail());


        return row;
    }
    public void setFilter(List<Member> membersItems){
        member_list=new ArrayList<>();
        member_list.addAll(membersItems);
        notifyDataSetChanged();
    }
}
