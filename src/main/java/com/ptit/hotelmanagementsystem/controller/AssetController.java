package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.AssetDto;
import com.ptit.hotelmanagementsystem.dto.CreateAssetRequest;
import com.ptit.hotelmanagementsystem.dto.UpdateAssetRequest;
import com.ptit.hotelmanagementsystem.service.AssetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@Tag(name = "Asset Management")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetDto> createAsset(@RequestBody CreateAssetRequest request) {
        AssetDto createdAsset = assetService.createAsset(request);
        return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDto> getAssetById(@PathVariable Long id) {
        return assetService.getAssetById(id)
                .map(assetDto -> new ResponseEntity<>(assetDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AssetDto>> getAllAssets() {
        List<AssetDto> assets = assetService.getAllAssets();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDto> updateAsset(@PathVariable Long id, @RequestBody UpdateAssetRequest request) {
        return assetService.updateAsset(id, request)
                .map(assetDto -> new ResponseEntity<>(assetDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        if (assetService.deleteAsset(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
