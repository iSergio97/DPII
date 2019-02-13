
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FinderService;
import services.FixUpTaskCategoryService;
import services.HandyWorkerService;
import services.WarrantyService;
import domain.Finder;
import domain.FixUpTask;
import domain.FixUpTaskCategory;
import domain.HandyWorker;
import domain.Warranty;

@Controller
//Se especifica que esta clase es un controlador	
@RequestMapping("/finder/handyWorker")
//Un contrlador que sirve para las llamadas relacionadas con esta url
public class FinderController extends AbstractController {

	@Autowired
	private FinderService				finderService;				//Vamos a requerir del servicio de finders

	@Autowired
	private HandyWorkerService			handyWorkerService;		//Servicio de los handy worker

	@Autowired
	private FixUpTaskCategoryService	fixUpTaskCategoryService;	//Necesitamos el servicio de categorias

	@Autowired
	private WarrantyService				warrantyService;			//necesitamos el serivicio de warrantys


	//TODO ARREGLAR
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {

		final ModelAndView result;
		final Finder finder;

		finder = this.finderService.findByHandyWorkerId(LoginService.getPrincipal().getId());

		result = new ModelAndView("finder/show");
		result.addObject("finder", finder);
		result.addObject("requestURI", "finder/handy-worker/show.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Finder finder;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		finder = this.finderService.findByHandyWorkerId(handyWorker.getId());
		//Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	/* Los finder no tienen metodo delete -------------------------------------- */

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Finder finder, final BindingResult binding) {
	//
	//		ModelAndView result;
	//
	//		try {
	//			this.finderService.delete(finder);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(finder, "finder.commit.error");
	//		}
	//
	//		return result;
	//	}

	//Este metodo es un bypass a un segundo meodo con 2 valores de entrada
	protected ModelAndView createEditModelAndView(final Finder finder) {

		ModelAndView result;
		result = this.createEditModelAndView(finder, null);

		return result;
	}

	//Este metodo construye el objeto modelandview en funcion del finder de entrada y los mensajes de error
	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {

		final ModelAndView result;
		FixUpTaskCategory category;
		final Collection<FixUpTaskCategory> categories;
		Warranty warranty;
		Collection<Warranty> warranties;

		categories = this.fixUpTaskCategoryService.findAll();
		warranties = this.warrantyService.findAll();
		if (finder.getWarranty() == null)
			warranty = null;
		else
			warranty = finder.getWarranty();
		if (finder.getFixUpTaskCategory() == null)
			category = null;
		else
			category = finder.getFixUpTaskCategory();
		result = new ModelAndView("finder/handyWorker/edit");
		result.addObject("finder", finder);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "finder/handyWorker/listfixuptasks")
	public ModelAndView getFinder() {

		final ModelAndView result;
		final Finder finder;

		finder = this.finderService.findByHandyWorkerId(LoginService.getPrincipal().getId());
		final Collection<FixUpTask> fut = finder.getFixUpTasks();

		result = new ModelAndView("finder/listfixuptasks");
		result.addObject("fixuptasks", fut);
		result.addObject("finder", finder);
		result.addObject("requestURI", "finder/handyWorker/listfixuptasks");

		return result;
	}
}
