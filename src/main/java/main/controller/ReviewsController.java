/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.ReviewsRequestDTO;
import main.dto.ReviewsResponseDTO;
import main.dto.ReviewsUpdateRequestDTO;
import main.page_dtos.ReviewsDTOPage;
import main.service.ReviewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/reviews")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@Tag(name="Reviews", description=" Reviews Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class ReviewsController {
      ReviewsService service;
    
    @Operation(summary="Retrieve All Reviews by rpdouct id", description="Paginated Retrieval for all reviews by product id")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of reviews is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Review List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/product_id/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<Page<ReviewsResponseDTO>> findAll(
            @Parameter(description = "The page number to retrieve", example = "0")
            @RequestParam(defaultValue = "0") int page,
        
            @Parameter(description = "The number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description="Id of product to retrieve reviews for", required=true)
            @PathVariable Integer productId){
        var result = service.findAll(page, size,productId);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    @Operation(summary="Retrieve All Reviews by product name ", description="Paginated Retrieval for all reviews by product name")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of reviews is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Review List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/product_name/{productName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReviewsResponseDTO>> findAllByName(
            @Parameter(description = "The page number to retrieve", example = "0")
            @RequestParam(defaultValue = "0") int page,
        
            @Parameter(description = "The number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description="name of product to retrieve reviews for", required=true)
            @PathVariable String productName){
        var result = service.findAll(page, size,productName);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    
    @Operation(summary="Get Review By Id", description="Retrieve a single Review by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Review isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Review was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ReviewsResponseDTO> findById(
            @Parameter(description = "Id of the review to retrieve", required = true)
            @PathVariable Integer id){
        var review = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(review);
       
    }
    
    @Operation(summary="Create a new  Review")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Review is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewsResponseDTO> create(
            @Parameter(description = "review to create", required = true)
            @Valid @RequestBody ReviewsRequestDTO x){
        var review = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
      
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update Review")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Review isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Review was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<ReviewsResponseDTO> update(
            @Parameter(description = "Id of the review to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedReview details", required = true)
            @Valid @RequestBody ReviewsUpdateRequestDTO x){
        var updatedReview = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedReview);
      
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete Review By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Review isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="Review was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the Review to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
}
