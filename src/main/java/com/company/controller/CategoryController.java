package com.company.controller;

import dto.CategoryDTO;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/category")
@Api(tags = "Category")
public class CategoryController {


    private final CategoryService categoryService;


    @ApiOperation(value = "Get", notes = "Method used for get category")
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> get(@PathVariable("categoryId") String categoryId) {
        log.info("/{categoryId} {}", categoryId);
        return ResponseEntity.ok(categoryService.get(categoryId));
    }


    @ApiOperation(value = "Create", notes = "Method used for create category",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO requestDTO,
                                    HttpServletRequest request) {
        log.info("CREATE {}{}", requestDTO, CategoryController.class);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.created(requestDTO));
    }


    @ApiOperation(value = "update", notes = "Mathod used for update",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/adm{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CategoryDTO updateDTO,
                                    @PathVariable("id") String id,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("Category_update: {}{}", updateDTO, CategoryController.class);
        return ResponseEntity.ok(categoryService.update(id, updateDTO));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete category",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }


    @ApiOperation(value = "List", notes = "Method used for get list of category")
    @GetMapping("/public")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(categoryService.list());
    }
}
