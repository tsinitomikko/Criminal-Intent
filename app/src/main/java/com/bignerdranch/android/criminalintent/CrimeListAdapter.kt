package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeEmptyListBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import java.util.UUID

class CrimeHolder(
    private val binding: ListItemCrimeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            onCrimeClicked(crime.id)
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class EmptyCrimeHolder(
    private val binding: FragmentCrimeEmptyListBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(onNewCrimeClicked: () -> Unit) {
        binding.addNewCrime.setOnClickListener {
            onNewCrimeClicked()
        }
    }
}

class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onCrimeClicked: (crimeId: UUID) -> Unit,
    private val onNewCrimeClicked: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (crimes.isEmpty()) {
            val binding = FragmentCrimeEmptyListBinding.inflate(inflater, parent, false)
            EmptyCrimeHolder(binding)
        } else {
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            CrimeHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (crimes.isEmpty()) {
            1
        } else {
            crimes.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (crimes.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_EMPTY) {
            holder as EmptyCrimeHolder
            holder.bind(onNewCrimeClicked)
        } else {
            val crime = crimes[position]
            holder as CrimeHolder
            holder.bind(crime, onCrimeClicked)
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_ITEM = 1
    }
}