package com.bignerdranch.android.criminalintent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemSeriousCrimeBinding

class CrimeHolder(
    private val context: Context,
    private val binding: ListItemCrimeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                String.format(context.getString(R.string.crime_clicked), crime.title),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class SeriousCrimeHolder(
    private val context: Context,
    private val binding: ListItemSeriousCrimeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                String.format(context.getString(R.string.crime_clicked), crime.title),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.contactPoliceButton.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                String.format(context.getString(R.string.contact_police_clicked), crime.title),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class CrimeListAdapter(private val context: Context, private val crimes: List<Crime>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val crimeBinding = ListItemCrimeBinding.inflate(inflater, parent, false)
        val seriousCrimeBinding = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)

        return when (viewType) {
            R.layout.list_item_crime -> CrimeHolder(context, crimeBinding)
            R.layout.list_item_serious_crime -> SeriousCrimeHolder(context, seriousCrimeBinding)
            else -> throw IllegalArgumentException("Unsupported layout")
        }
    }

    override fun getItemCount(): Int = crimes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]

        when (holder) {
            is CrimeHolder -> {
                holder.bind(crime)
            }

            is SeriousCrimeHolder -> {
                holder.bind(crime)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]

        return when (crime.requiresPolice) {
            true -> R.layout.list_item_serious_crime
            false -> R.layout.list_item_crime
        }
    }
}