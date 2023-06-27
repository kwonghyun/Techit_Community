package likelion15.mutsa.controller;

import likelion15.mutsa.dto.NoticeDto;
import likelion15.mutsa.entity.Notice;
import likelion15.mutsa.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(
            NoticeService noticeService
    ){
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String getNotice(Model model) {
        model.addAttribute(
                "noticeList",
                        noticeService.readNoticeAll()
        );
        return "notice";
    }

    @GetMapping("/notice/add-view")
    public String addNoticeView() {
        return "add";
    }

    @PostMapping("/notice/add")
    public String addNotice(NoticeDto noticeDto) {
        Notice notice = noticeService.createNotice(noticeDto);

        return "redirect:/notice";
    }

    @GetMapping("/notice/{id}")
    public String read(
            @PathVariable("id") Long id,
            Model model
    ) {
//        studentService.readStudent(id);
        model.addAttribute(
                "notice",
                noticeService.readNotice(id)

        );

        return "read";
    }




    @GetMapping("/notice/{id}/update-view")
    public String updateView(

            @PathVariable("id") Long id,
            Model model
    ){

        NoticeDto dto = noticeService.readNotice(id);
        model.addAttribute("notice", dto);


        return "update";
    }

    @PostMapping("/notice/{id}/update")
    public String update(

            @PathVariable("id") Long id,

            NoticeDto noticeDto
    ) {
        // service 활용하기
        noticeService.updateNotice(id, noticeDto);
        // 상세보기 페이지로 redirect
        return String.format("redirect:/notice/%s", id);
    }

    // TODO
    // deleteView 메소드 만들기
    // GetMapping 을 써서...
    // Long id는 어떻게...
    // studentDto 를 가지고...
    // return...
    @GetMapping("/notice/{id}/delete-view")
    public String deleteView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        NoticeDto noticeDto
                = noticeService.readNotice(id);
        model.addAttribute("notice", noticeDto);
        return "delete";
    }

    @PostMapping("/notice/{id}/delete")
    public String delete(
            @PathVariable("id")
            Long id
    ) {
        noticeService.deleteNotice(id);
        // update 때는 데이터가 남아있지만
        // delete 는 돌아갈 상세보기가 없다
        // 그래서 홈으로 돌아간다.
        return "redirect:/notice";
    }

}