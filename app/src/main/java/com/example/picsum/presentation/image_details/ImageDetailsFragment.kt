package com.example.picsum.presentation.image_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.picsum.R
import com.example.picsum.databinding.FragmentImageDetailsBinding

class ImageDetailsFragment: Fragment() {

    private var _binding: FragmentImageDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: ImageDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            image = args.image

            imageDownloadUrl.run {
                paint.isUnderlineText = true
                setOnClickListener {
                    requireContext().startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(args.image.downloadUrl))
                    )
                }
            }
        }
    }
}