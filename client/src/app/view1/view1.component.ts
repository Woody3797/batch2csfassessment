import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UtilityService } from '../utility.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit {

    fb = inject(FormBuilder)
    utility = inject(UtilityService)
    router = inject(Router)


    form!: FormGroup


    ngOnInit(): void {
        this.form = this.fb.group({
            name: this.fb.control<string>('', [Validators.required]),
            title: this.fb.control<string>('', [Validators.required]),
            comments: this.fb.control<string>(''),
            archive: this.fb.control<File | null>(null, [Validators.required])
        })
    }
    
    process1() {
        let f = this.form.value
        this.utility.upload1(f['name'], f['title'], f['comments'], f['archive'])
        this.router.navigate(['/uploaded'])
    }
}
