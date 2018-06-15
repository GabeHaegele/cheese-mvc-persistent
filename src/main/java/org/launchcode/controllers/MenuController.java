package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    MenuDao menuDao;
    // Request path: /menu
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "My Menus");

        return "menu/index";
    }
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(@ModelAttribute @Valid Menu newMenu,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            return "menu/add";
        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String displayMenu(Model model, @PathVariable Integer id) {
        model.addAttribute("menu", menuDao.findOne(id));
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable Integer id) {
        AddMenuItemForm newForm = new AddMenuItemForm(menuDao.findOne(id));
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("form", newForm);
        model.addAttribute("title", "Add item to menu: " + newForm.getMenu().getName());
        return "menu/add-item";
    }
    @RequestMapping(value = "add-item/{id}", method = RequestMethod.POST)
    public String addItem(@ModelAttribute(value = "form") @Valid AddMenuItemForm form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add item to menu: " + form.getMenu().getName());
            return "menu/add-item";
        }
        menuDao.findOne(form.getMenuId()).addItem(cheeseDao.findOne(form.getCheeseId()));
        menuDao.save(menuDao.findOne(form.getMenuId()));
        return "redirect:/menu/view/"+menuDao.findOne(form.getMenuId()).getId();
    }
}