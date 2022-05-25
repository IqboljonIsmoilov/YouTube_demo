package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@Api(tags = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private Logger log = LoggerFactory.getLogger(CategoryController.class);


    @ApiOperation(value = "Get", notes = "Method used for get category")
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> get(@PathVariable("categoryId") String categoryId) {
        log.info("/{categoryId} {}", categoryId);
        return ResponseEntity.ok(categoryService.get(categoryId));
    }


    @ApiOperation(value = "Create", notes = "Method used for create category",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto,
                                    HttpServletRequest request) {
        log.info("CREATE {}", dto);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.created(dto));
    }


    @ApiOperation(value = "update", notes = "Mathod used for update",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/adm{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CategoryDTO dto,
                                    @PathVariable("id") String id,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("Category_update: {}", dto);
        return ResponseEntity.ok(categoryService.update(id, dto));
    }


    @ApiOperation(value = "Delete", notes = "Method used for delete category",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/adm/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        log.info("DELETE {}", id);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }


    @ApiOperation(value = "List", notes = "Method used for get list of category")
    @GetMapping("/public")
    public ResponseEntity<?> list() {
        log.info("/public");
        return ResponseEntity.ok(categoryService.list());
    }
}
