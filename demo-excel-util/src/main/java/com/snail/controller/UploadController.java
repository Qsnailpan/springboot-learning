package com.snail.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.snail.util.excel.ExcelUtil;

@Controller
public class UploadController {

	private static String UPLOADED_FOLDER = "F://temp//";

	@GetMapping("/")
	public String index() {
		return "upload";
	}
   /**
    *  上传文件
    * @param file
    * @param redirectAttributes
    * @return
    */
	@PostMapping("/upload") 
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message",
					"successfully uploaded '" + file.getOriginalFilename() + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}

	/**
	 *  上传excel 解析表中数据
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "uploadExcel", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {

			Iterator<String> filenames = request.getFileNames();
			while (filenames.hasNext()) {
				MultipartFile file = request.getFile(filenames.next());
				InputStream is = file.getInputStream();
				// 第一步 ，解析数据为map <行号,数据内容>
				Map<Integer, List<String>> datas = ExcelUtil.parseExcel(is, 2);
				// 第二步，把list<String> 转化为 dto 對象，并检查数据的准确性
				// 第三步 入库
				System.out.println(datas);
			}
			redirectAttributes.addFlashAttribute("message", "success 请查看后台解析");
			return "redirect:/uploadStatus";
		} catch (Exception e) {
			return "redirect:/uploadStatus";
		}
	}

   
    @PostMapping(value = "batchImport")
    public String batchImportUserKnowledge(@RequestParam(value="file") MultipartFile file, RedirectAttributes redirectAttributes,
            HttpServletRequest request,HttpServletResponse response) throws IOException{
		
        //判断文件是否为空
        if(file==null){ }
        
        //获取文件名
        String fileName=file.getOriginalFilename();
        //验证文件名是否合格
        
        //进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null
        
        //批量导入
        String message = ExcelUtil.batchImport(fileName,file,"admin");
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:toUserKnowledgeImport";
    }
    
	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

}