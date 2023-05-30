import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { View1Component } from './view1/view1.component';
import { View0Component } from './view0/view0.component';
import { View2Component } from './view2/view2.component';

export const routes: Routes = [
    {path: '', component: View0Component },
    {path: 'view1', component: View1Component },
    {path: 'view2/:bundleId', component: View2Component },
    {path: '**', redirectTo: '/', pathMatch: 'full' }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes, { useHash: false }),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
