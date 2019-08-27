package com.demo.activiti.springactiviti.api;

import com.demo.activiti.springactiviti.service.ActModelService;
import com.demo.activiti.springactiviti.utils.HeadUtil;
import com.demo.activiti.springactiviti.utils.PaginationUtil;
import com.demo.activiti.springactiviti.vo.ActModelVo;
import io.github.jhipster.web.util.ResponseUtil;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @Author Administrator
 * @Description TODO
 * @Date 2019/8/20 10:49
 * @ClassName ActModelResource
 **/
@RestController
@RequestMapping("/api")
public class ActModelResource {

    private final Logger log = LoggerFactory.getLogger(ActModelResource.class);

    private static final String ENTITY_NAME = "model";

    @Autowired
    private ActModelService actModelService;

    /**
     * 创建模型
     * @param actModelVO
     * @return the ResponseEntity with status 201 (Created) and with body the new model
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/models")
    public ResponseEntity<Model> createModel(@Valid @RequestBody ActModelVo actModelVO) throws UnsupportedEncodingException, URISyntaxException {
        log.debug("REST request to save Model : {}", actModelVO);
        Model result = actModelService.createModel(actModelVO);
        return ResponseEntity.created(new URI("/act/models/" + result.getId()))
                .headers(HeadUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * 根据模型id查询
     * @param modelId 模型id
     * @return
     */
    @GetMapping("/models/{modelId}")
    public ResponseEntity<Model> getModel(@PathVariable String modelId){
        log.debug("REST request to get Model : {}", modelId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actModelService.getModelById(modelId)));
    }

    /**
     * 查询模型列表
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all models
     */
    @GetMapping("/models")
    public ResponseEntity<List<Model>> getAllModels(Pageable pageable) {
        final Page<Model> page = actModelService.getAllModels(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page ,"/act/models/");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * 部署模型
     * @param actModelVO
     * @return
     */
    @PutMapping("/models/deploy")
    public ResponseEntity<Void> deployModel(@RequestBody ActModelVo actModelVO){
        String modelId = actModelVO.getId();
        log.debug("REST request to deploy Model : {}", modelId);
        try {
            List<ProcessDefinition> processDefinitions = actModelService.deployModel(modelId);
            if(!CollectionUtils.isEmpty(processDefinitions)){
                return ResponseEntity.ok().headers(HeadUtil.createAlert("A " + ENTITY_NAME + " is deployed with identifier " + modelId, modelId)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(HeadUtil.createFailureAlert(ENTITY_NAME, modelId,"A " + ENTITY_NAME + " failed to deploy with identifier " + modelId)).build();
    }

    /**
     * 删除模型
     * @param modelId 模型id
     * @return
     */
    @DeleteMapping("/models/{modelId}")
    public ResponseEntity<Void> deleteModel(@PathVariable String modelId) {
        log.debug("REST request to delete Model: {}", modelId);
        actModelService.deleteModel(modelId);
        return ResponseEntity.ok().headers(HeadUtil.createEntityDeletionAlert(ENTITY_NAME, modelId)).build();
    }

    /**
     * 导出模型xml文件
     * @param modelId 模型id
     * @param response HttpServletResponse
     * @throws IOException
     */
    @GetMapping("/models/export")
    public void exportModel(@RequestParam("modelId")String modelId, HttpServletResponse response) throws IOException {
        actModelService.exportModel(modelId, response);
    }



}
