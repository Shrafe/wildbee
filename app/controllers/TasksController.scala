package controllers

import play.api._
import play.api.mvc._

import models._

import play.api.data._
import play.api.data.Forms._

object TasksController extends Controller {

  val taskForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "owner_id" -> nonEmptyText)(NewTask.apply)(NewTask.unapply))

  def create = Action {
    implicit request =>
      taskForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.tasks.newEntity(formWithErrors)),
        newTask => {
          Tasks.insert(newTask.name, newTask.owner)
          Redirect(routes.TasksController.show(newTask.name))
        }
      )
  }
  // TODO: Add workflow to this
  def newTask() = Action {
    Ok(views.html.tasks.newEntity(taskForm))
  }
  // TODO: add update task

  def show(taskName: String) = Action {
    Ok(views.html.tasks.show(Tasks.findByTask(taskName)))
  }
  // TODO: Integrate workflow with this
  def delete(name: String) = Action { implicit request =>
  //  database withSession { 
  //    PackageStatuses.delete(name) //Should be a package thing
  //    Workflows.delete(name)       //Delete data dependent on a task first
  //    Tasks.delete(name) 
  //  }
  //  Redirect(routes.TasksController.index)
  Ok("TODO")
 }
  
  // TODO: review this Dustin!
  // def updateWorkflow(name :String) = Action { implicit request =>
  //  workForm.bindFromRequest.fold(
  //    errors => BadRequest(views.html.index("Error Updating :: " + errors)),
  //    w      => database withSession { Workflows.create(name, w.stage) }
  //  )   
  //  Redirect(routes.TasksController.show(name))
  // }
}
