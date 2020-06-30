package com.example.bod_members.Bod;

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


public class BODListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<BOD> bodList;

    public BODListAdapter(Context context, int layout, ArrayList<BOD> bodList) {
        this.context = context;
        this.layout = layout;
        this.bodList = bodList;
    }

    @Override
    public int getCount() {
        return bodList.size();
    }

    @Override
    public Object getItem(int position) {
        return bodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, textDeveloper,textPosition,textYear,textContact;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.textName);
            holder.textDeveloper = (TextView) row.findViewById(R.id.textDeveloper);
            holder.imageView = (ImageView) row.findViewById(R.id.image_food);
            holder.textPosition=(TextView)row.findViewById(R.id.textPosition);
            holder.textYear=(TextView) row.findViewById(R.id.textYear);
            holder.textContact=(TextView) row.findViewById(R.id.textContact);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        BOD food = bodList.get(position);

        holder.txtName.setText(food.getName());
        holder.textDeveloper.setText(food.getDeveloper());
        byte[] foodImage = food.getPhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.textPosition.setText(food.getPosition());
        holder.imageView.setImageBitmap(bitmap);
        holder.textYear.setText(food.getYear());
        holder.textContact.setText(food.getContact());


        return row;
    }
    public void setFilter(List<BOD> BODItems){
        bodList=new ArrayList<>();
        bodList.addAll(BODItems);
        notifyDataSetChanged();
    }
}
