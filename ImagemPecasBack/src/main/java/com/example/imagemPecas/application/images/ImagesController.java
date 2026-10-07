package com.example.imagemPecas.application.images;

import com.example.imagemPecas.domain.Service.ImageService;
import com.example.imagemPecas.domain.entity.Image;
import com.example.imagemPecas.domain.enums.ImageExtension;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService service;
    private final ImageMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
    ) throws IOException {

        log.info(
                "Imagem recebida: name: {}, size: {}",
                file.getOriginalFilename(),
                file.getSize()
        );

        Image image = mapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);

        URI imageUri = buildImageURL(savedImage);

        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false) String extension,
            @RequestParam(value = "query", required = false) String query
    ) {

        var result = service.search(
                ImageExtension.ofName(extension),
                query
        );

        var images = result.stream()
                .map(image -> {
                    var url = buildImageURL(image);

                    return mapper.imageToDTO(
                            image,
                            url.toString()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable("id") String id
    ) {

        var possibleImage = service.getById(id);

        if (possibleImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var image = possibleImage.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData(
                "inline; filename=\"" + image.getName() + "\"",
                image.getFileName()
        );

        return new ResponseEntity<>(
                image.getFile(),
                headers,
                HttpStatus.OK
        );
    }

    private URI buildImageURL(Image image) {
        String imagePath = "/" + image.getId();

        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(imagePath)
                .build()
                .toUri();
    }
}
