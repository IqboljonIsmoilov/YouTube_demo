package com.company.service;

import dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.DownloadNotFoundException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AttachService {

    private final AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String attachFolder;

    @Value("${server.domain.name}")
    private String domainName;

    public AttachDTO upload(MultipartFile file) {
        String pathFolder = getYmDString();

        File folder = new File(attachFolder + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());
        AttachEntity entity = saveAttach(key, pathFolder, extension, file);
        AttachDTO dto = toDTO(entity);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + pathFolder + "/" + key + "." + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }


    public Boolean delete(String id) {
        AttachEntity entity = get(id);

        File file = new File(attachFolder + entity.getPath() +
                "/" + entity.getId());
        if (file.delete()) {
            attachRepository.deleteById(id);
            return true;
        } else
            throw new AppBadRequestException("Could not read the file!");
    }


    public byte[] open(String id) {
        byte[] data;
        try {
            AttachEntity entity = get(id);
            String path = entity.getPath() + "/" + id;
            Path file = Paths.get(attachFolder + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    public AttachEntity getById(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            return new ItemNotFoundException("Not found!");
        });
    }


    public ResponseEntity<Resource> download(String id) {
        try {
            AttachEntity entity = get(id);
            String path = entity.getPath() + "/" + id;
            Path file = Paths.get(attachFolder + path);
            System.out.println(file.toUri());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + entity.getOrigenName() + "\"")
                        .body(resource);

            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new DownloadNotFoundException("Error: " + e.getMessage());
        }
    }


    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not found");
        });
    }


    public AttachDTO getId(String id) {
        return toDTO(attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not found");
        }));
    }


    public AttachEntity saveAttach(String key, String pathFolder, String type, MultipartFile file) {
        AttachEntity entity = new AttachEntity();
        entity.setId(key);
        entity.setPath(pathFolder);
        entity.setOrigenName(file.getOriginalFilename());
        entity.setSize(file.getSize());
        entity.setType(type);

        attachRepository.save(entity);
        return entity;
    }


    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrigenName(entity.getOrigenName());
        dto.setPath(entity.getPath());
        dto.setType(entity.getType());

        return dto;
    }


    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }


    public List<AttachDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort
                .Direction.DESC, "createdDate"));

        List<AttachDTO> dtoList = new ArrayList<>();

        attachRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(todTO(entity));
        });
        return dtoList;
    }


    public AttachDTO todTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId().toString());
        dto.setPath(entity.getPath());
        dto.setOrigenName(entity.getOrigenName());
        dto.setUrl(domainName + "attach/download/" + entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}