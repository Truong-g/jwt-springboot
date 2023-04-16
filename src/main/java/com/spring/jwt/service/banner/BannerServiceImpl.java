package com.spring.jwt.service.banner;

import com.spring.jwt.dto.BannerFormRequest;
import com.spring.jwt.entity.Banner;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.BannerRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.files.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class BannerServiceImpl implements BannerService{

    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Optional<Banner> getBanner(Long id) {
        return bannerRepository.findById(id);
    }

    @Override
    public Banner addBanner(BannerFormRequest request) throws IOException {
        Banner banner = new Banner();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        banner.setUser(user);
        String pathImage = fileService.store(request.getImageFile());
        banner.setImageUrl(pathImage);
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(Long id, BannerFormRequest request) throws IOException{
        Banner banner = getBanner(id).orElseThrow();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        banner.setUser(user);
       if(!ObjectUtils.isEmpty(request.getImageFile())){
           String pathImage = fileService.store(request.getImageFile());
           banner.setImageUrl(pathImage);
       }
        return bannerRepository.save(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        Banner banner = getBanner(id).orElseThrow();
        bannerRepository.delete(banner);
    }
}
