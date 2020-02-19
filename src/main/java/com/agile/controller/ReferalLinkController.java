package com.agile.controller;

import com.agile.pojo.ReferalLink;
import com.agile.service.ReferalLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReferalLinkController {
    @Autowired
    private ReferalLinkService referalLinkService = null;

    @RequestMapping("/listLink")
    public String list(Model model) {
        List<ReferalLink> links = referalLinkService.listAll();
        model.addAttribute("links", links);
        return"admin/listLink";
    }
}
