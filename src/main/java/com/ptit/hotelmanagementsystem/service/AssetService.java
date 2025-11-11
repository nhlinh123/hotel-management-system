package com.ptit.hotelmanagementsystem.service;

import com.ptit.hotelmanagementsystem.dto.AssetDto;
import com.ptit.hotelmanagementsystem.dto.CreateAssetRequest;
import com.ptit.hotelmanagementsystem.dto.UpdateAssetRequest;
import com.ptit.hotelmanagementsystem.model.Asset;
import com.ptit.hotelmanagementsystem.model.Room;
import com.ptit.hotelmanagementsystem.repository.AssetRepository;
import com.ptit.hotelmanagementsystem.repository.RoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private RoomRepository roomRepository;

    public AssetDto createAsset(CreateAssetRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + request.getRoomId()));

        Asset asset = new Asset();
        BeanUtils.copyProperties(request, asset);
        asset.setRoom(room);
        Asset savedAsset = assetRepository.save(asset);
        return convertToDto(savedAsset);
    }

    public Optional<AssetDto> getAssetById(Long id) {
        return assetRepository.findById(id)
                .map(this::convertToDto);
    }

    public List<AssetDto> getAllAssets() {
        return assetRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<AssetDto> updateAsset(Long id, UpdateAssetRequest request) {
        return assetRepository.findById(id)
                .map(asset -> {
                    Room room = roomRepository.findById(request.getRoomId())
                            .orElseThrow(() -> new RuntimeException("Room not found with ID: " + request.getRoomId()));
                    BeanUtils.copyProperties(request, asset);
                    asset.setId(id);
                    asset.setRoom(room);
                    Asset updatedAsset = assetRepository.save(asset);
                    return convertToDto(updatedAsset);
                });
    }

    public boolean deleteAsset(Long id) {
        if (assetRepository.existsById(id)) {
            assetRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private AssetDto convertToDto(Asset asset) {
        AssetDto assetDto = new AssetDto();
        BeanUtils.copyProperties(asset, assetDto);
        assetDto.setRoomId(asset.getRoom().getId());
        return assetDto;
    }
}
