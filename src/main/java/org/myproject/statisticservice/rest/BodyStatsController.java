package org.myproject.statisticservice.rest;

import lombok.RequiredArgsConstructor;
import org.myproject.statisticservice.dto.*;
import org.myproject.statisticservice.service.BodyStatsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{userId}/body-stats")
@RequiredArgsConstructor
public class BodyStatsController {

    private final BodyStatsService bodyStatsService;

    @PostMapping
    public SelectDetailBodyStatDto createBodyStatRecord(@PathVariable Long userId,
                                     @RequestBody CreateUpdateBodyStatDto dto) {
        return bodyStatsService.createBodyStatRecord(userId, dto);
    }

    @GetMapping
    public Page<SelectBodyStatDto> findAllBodyStatsByUserId(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size,
                                                            @PathVariable Long userId) {
        return bodyStatsService.findAllBodyStatsByUserId(userId, PageRequest.of(page, size));
    }

    @PatchMapping("/{id}")
    public SelectDetailBodyStatDto updateBodyStatRecord(@PathVariable Long userId,
                                     @PathVariable Long id,
                                     @RequestBody CreateUpdateBodyStatDto dto) {
        return bodyStatsService.updateBodyStatRecord(userId, id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBodyStatRecord(@PathVariable Long userId,
                                     @PathVariable Long id) {
        bodyStatsService.deleteBodyStatRecord(userId, id);
    }

    @GetMapping("/{id}")
    public SelectDetailBodyStatDto findById(@PathVariable Long userId,
                                   @PathVariable Long id) {
        return bodyStatsService.findById(userId, id);
    }
}
