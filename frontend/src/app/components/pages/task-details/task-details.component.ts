import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Collaborator, CollaboratorService } from 'src/app/services/collaborator/collaborator.service';
import { Comment, CommentService } from 'src/app/services/comment/comment.service';
import { Task, TaskService } from 'src/app/services/task/task.service';
import { User, UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.scss']
})
export class TaskDetailsComponent implements OnInit {
  taskId: string;
  task: Task;
  projectId: string;
  collaborators: Array<Collaborator>;
  priorities: any;
  statuses: any;
  types: any;
  leftForm: FormGroup;
  rightForm: FormGroup;
  errorMessage: String;
  currentUser: User;

  comments: Array<Comment> = []
  commentsMetadata: any = []
  commentLoader = false;
  deleteCommentLoader = false;
  editCommentLoader = false;
  commentLoadMoreAvailable: boolean = true;

  descriptionSuccessMessage: string;
  descriptionErrorMessage: string;
  userPriorityStatusSuccessMessage: string;
  userPriorityStatusErrorMessage: string;

  constructor(private formBuilder: FormBuilder, private taskService: TaskService, private userService: UserService, 
    private collaboratorService: CollaboratorService, private route: ActivatedRoute, private commentService: CommentService) { 
    this.descriptionErrorMessage = '';
    this.descriptionSuccessMessage = '';
    this.userPriorityStatusErrorMessage = '';
    this.userPriorityStatusSuccessMessage = '';
  }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();

    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.taskId = params.taskId;
    });
    
    this.loadTask();
    this.loadComments();

    this.errorMessage = "";
  }

  loadTask() {
    this.taskService.getTaskById(this.taskId,
      (data: any) => this.onTaskLoad(data),
      (err: any) => console.log(err));
  }

  onTaskLoad(data) {
    this.task = data;
    this.leftForm = this.formBuilder.group({
      description: new FormControl(this.task.description, [Validators.required, Validators.maxLength(255)]),
      comment: new FormControl('', [Validators.required, Validators.maxLength(255)])
    });   

    this.rightForm = this.formBuilder.group({
      collaborator: new FormControl(),
      priority: new FormControl(),
      status: new FormControl(),
      type: new FormControl()
    });   

    this.loadCollaborators(); 
    this.loadPriorities();
    this.loadStatuses();
    this.loadTypes();
  }

  loadComments() {
    this.commentService.getComments(this.taskId, {}, 
      (response) => this.onLoadComments(response), 
      (error) => console.log(error)
    );
  }

  loadPriorities() {
    this.taskService.getPriorities(
      (data: any) => {
        this.priorities = data.data;
        this.rightForm.patchValue({priority: this.priorities.find(p => p.id === this.task.priority.id)});
      }
    )
  }

  loadTypes() {
    this.taskService.getTypes(
      (data: any) => {
        this.types = data.data;
        this.rightForm.patchValue({type: this.types.find(t => t.id === this.task.type.id)});
      } 
    )
  }

  loadStatuses() {
    this.taskService.getStatuses(
      (data: any) => {
        this.statuses = data.data;
        this.rightForm.patchValue({status: this.statuses.find(s => s.id === this.task.status.id)});
      }  
    )
  }

  loadCollaborators() {
    this.collaboratorService.getCollaborators(this.projectId,
      (data: any) => this.onCollaboratorsLoad(data), 
      () => this.userPriorityStatusErrorMessage = 'Error while loading data. Please try again later.');
  }

  onCollaboratorsLoad(data: any): any {
    this.collaborators = data;
    if (this.task.userId === null) 
      this.rightForm.patchValue({collaborator: null});
    else 
      this.rightForm.patchValue({collaborator: this.collaborators.find(c => c.collaboratorId === this.task.userId).collaborator});
  }

  patchDescription(description: string) {
    this.taskService.patchTask(this.taskId, 
      {
        description: description
      }, 
      (data: any) => {
        this.descriptionSuccessMessage = "Description successfully changed";
        setTimeout(() => this.descriptionSuccessMessage = '', 1800);
      },
      (err: any) => { 
        this.descriptionErrorMessage = err.error.errors.message;
        setTimeout(() => this.descriptionErrorMessage = '', 1800);
      }
    );
  }

  addComment(commentText: string) {
    this.commentLoader = true;
    this.commentService.addComment(this.taskId, commentText,  
      (response) => {
        this.loadComments();
        this.commentLoader = false;
        this.leftForm.get("comment").reset();
      },
      (error) => {console.log(error)})
  }

  deleteComment() {
    this.loadComments();
  }

  editComment() {
    this.loadComments();
  }

  patchUserPriorityStatus() {
    const form  = this.rightForm.getRawValue();
    this.taskService.patchTask(this.taskId, 
      {
        user_id: form.collaborator === null ? null : form.collaborator.id,
        priority_id: form.priority.id,
        status_id: form.status.id
      }, 
      (data: any) => {
        this.userPriorityStatusSuccessMessage = "Task details successfully changed";
        setTimeout(() => this.userPriorityStatusSuccessMessage = '', 1800);
      },
      (err: any) => { 
        this.userPriorityStatusErrorMessage = err.error.errors.message;
        setTimeout(() => this.userPriorityStatusErrorMessage = '', 1800);
      }
    );
  }

  subscribe() {
    console.log("subscribe");
  }

  onLoadComments(response: any, reset: boolean = true) {
    if(reset) this.comments = [];
    response.data.forEach(comment => {
      this.comments.push({
        id: comment.id,
        text: comment.text,
        userId: comment.user_id,
        userFirstName: comment.user_first_name,
        userLastName: comment.user_last_name,
        createdAt: comment.created_at
      });
    });

    this.commentsMetadata = response.metadata;
    if(!response.metadata.has_next) this.commentLoadMoreAvailable = false;
    else this.commentLoadMoreAvailable = true;
  }

  paginate() {
    if(this.commentsMetadata.has_next) {
      const paginationOptions = {page: this.commentsMetadata.page_number+1, size: this.commentsMetadata.page_size}
      this.commentService.getComments(this.taskId, paginationOptions, 
        (response) => this.onLoadComments(response, false), 
        (error) => console.log(error)
      );
    }else {
      this.commentLoadMoreAvailable = false;
    }
  }
}
