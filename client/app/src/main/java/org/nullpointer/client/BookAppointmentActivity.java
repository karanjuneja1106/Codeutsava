package org.nullpointer.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity {
    private RecyclerView mDocRecyclerView;
    private Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        mDocRecyclerView = (RecyclerView)findViewById(R.id.doc_list);
        mDocRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }
    private	class	Holder	extends	RecyclerView.ViewHolder	implements View.OnClickListener{
        private TextView mNameTextView;
        private TextView mContactTextView;
        public	Holder(View itemView)	{
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.name);
            mContactTextView = (TextView) itemView.findViewById(R.id.contact);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            Intent i = new Intent(getApplicationContext(),CnfAppointmentActivity.class);
            i.putExtra("NAME",mNameTextView.getText());
            i.putExtra("CONTACT",mContactTextView.getText());
            startActivity(i);
        }
    }
    private	class Adapter extends RecyclerView.Adapter<Holder>	{
        private List<Doctor> mDocs;
        public	Adapter(List<Doctor> docs)
        {
            mDocs = docs;
        }
        @Override
        public	Holder	onCreateViewHolder(ViewGroup parent, int	viewType)	{
            LayoutInflater layoutInflater	=	LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.list_item_doc, parent, false);
            return	new	Holder(view);
        }
        @Override
        public	void	onBindViewHolder(Holder holder, int position)	{
            Doctor doc	=	mDocs.get(position);
            holder.mNameTextView.setText(doc.getName());
            holder.mContactTextView.setText(doc.getContact());
        }
        @Override
        public	int	getItemCount()	{
            return	mDocs.size();
        }
    }
    private	void	updateUI()	{
        List<Doctor> docs=new getDoctorList().getDocList();
        mAdapter = new Adapter(docs);
        mDocRecyclerView.setAdapter(mAdapter);
    }
}
