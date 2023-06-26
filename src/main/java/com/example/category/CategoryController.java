package com.example.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Category;
import com.example.security.SLShopUserDetails;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}



	 /**
     * 管理者一覧画面表示
     *
     * @param model
     * @return 管理者一覧画面
     */
    @GetMapping
    public String listCategories(@RequestParam(required = false) String keyword, Model model) {
        // 全管理者情報の取得
        List<Category> listCategories = categoryService.listAll(keyword);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("keyword", keyword);
        return "categories/categories";
    }


    /**
     * カテゴリー新規登録画面表示
     *
     * @param model
     * @return カテゴリー新規登録画面
     */
    @GetMapping("/new")
    public String newCategory(Model model) {
        // 新規登録用に、空のカテゴリー情報作成
        Category category = new Category();
        model.addAttribute("category", category);
        return "categories/category_form";
    }



    /**
     * 管理者登録・更新処理
     *
     * @param Category 管理者情報
     * @param ra
     * @return 管理者一覧画面
     */
    @PostMapping("/save")
    public String saveCategory(Category category, RedirectAttributes ra) {
        // 管理者情報の登録
        categoryService.save(category);
        // 登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "登録に成功しました");
        return "redirect:/categories";
    }





    /**
     * カテゴリー編集画面表示
     *
     * @param id カテゴリーID
     * @param model
     * @return カテゴリー編集画面
     */
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Long id, Model model) {
        // カテゴリーIDに紐づくカテゴリー情報取得
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "categories/category_edit";
    }


    /**
     * 管理者詳細画面表示
     *
     * @param id 管理者ID
     * @param model
     * @return 管理者詳細画面
     */
    @GetMapping("/detail/{id}")
    public String detailUser(@PathVariable(name = "id") Long id, Model model) {
        // 管理者IDに紐づく管理者情報取得
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "categories/category_detail";
    }




    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id, @AuthenticationPrincipal SLShopUserDetails categoryDetails, Model model, RedirectAttributes ra) {
    	// 管理者情報削除
        categoryService.delete(id);
        // 削除成功のメッセージを格納
        ra.addFlashAttribute("success_message", "削除に成功しました");
            return "redirect:/categories";
        }

}


