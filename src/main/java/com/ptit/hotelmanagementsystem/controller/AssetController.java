package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.AssetDto;
import com.ptit.hotelmanagementsystem.dto.CreateAssetRequest;
import com.ptit.hotelmanagementsystem.dto.UpdateAssetRequest;
import com.ptit.hotelmanagementsystem.dto.BaseResponseModel;
import com.ptit.hotelmanagementsystem.service.AssetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@Tag(name = "Asset Management")
@SecurityRequirement(name = "Bearer Authentication")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping
    public ResponseEntity<BaseResponseModel<AssetDto>> createAsset(@RequestBody CreateAssetRequest request) {
        AssetDto createdAsset = assetService.createAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseModel.created(createdAsset, "Asset created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel<AssetDto>> getAssetById(@PathVariable Long id) {
        return assetService.getAssetById(id)
                .map(assetDto -> ResponseEntity.ok(BaseResponseModel.success(assetDto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("Asset not found")));
    }

    @GetMapping
    public ResponseEntity<BaseResponseModel<List<AssetDto>>> getAllAssets() {
        List<AssetDto> assets = assetService.getAllAssets();
        return ResponseEntity.ok(BaseResponseModel.success(assets));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel<AssetDto>> updateAsset(@PathVariable Long id, @RequestBody UpdateAssetRequest request) {
        return assetService.updateAsset(id, request)
                .map(assetDto -> ResponseEntity.ok(BaseResponseModel.success(assetDto, "Asset updated successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("Asset not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel<String>> deleteAsset(@PathVariable Long id) {
        if (assetService.deleteAsset(id)) {
            return ResponseEntity.ok(BaseResponseModel.success("Asset deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponseModel.notFound("Asset not found"));
    }
}
