package com.spring.jwt.controller;


import com.spring.jwt.dto.BannerFormRequest;
import com.spring.jwt.dto.CustomResponse;
import com.spring.jwt.entity.Banner;
import com.spring.jwt.service.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Banner>>> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), "success", banners);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<List<Banner>>> addBanner(@ModelAttribute BannerFormRequest request) throws IOException {
        Banner banner = bannerService.addBanner(request);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), "success", banner);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> getAllBanners(@PathVariable Long id) {
       bannerService.deleteBanner(id);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), "success", null);
        return ResponseEntity.ok(response);
    }

}
