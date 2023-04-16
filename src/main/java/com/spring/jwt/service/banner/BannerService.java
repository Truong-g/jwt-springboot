package com.spring.jwt.service.banner;

import com.spring.jwt.dto.BannerFormRequest;
import com.spring.jwt.entity.Banner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BannerService {
    List<Banner> getAllBanners();
    Optional<Banner> getBanner(Long id);

    Banner addBanner(BannerFormRequest request) throws IOException;
    Banner updateBanner(Long id, BannerFormRequest request) throws IOException;
    void deleteBanner(Long id);
}
