package com.bignerdranch.android.criminalintent

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import java.util.UUID

class CrimeHolder(
    private val binding: ListItemCrimeBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.root.contentDescription = getCrimeReportSummary(context, crime)
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

    private fun getCrimeReportSummary(context: Context, crime: Crime): String {
        val solvedString = if (crime.isSolved) {
            context.getString(R.string.crime_report_solved)
        } else {
            context.getString(R.string.crime_report_unsolved)
        }

        val dateString = DateFormat.format("EEEE,  MMM, d yyyy", crime.date).toString()

        return context.getString(
            R.string.crime_report_summary,
            crime.title, dateString, solvedString
        )
    }
}

class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onCrimeClicked: (crimeId: UUID) -> Unit,
) : RecyclerView.Adapter<CrimeHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        return CrimeHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = crimes.size

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.bind(crime, onCrimeClicked)
    }
}

