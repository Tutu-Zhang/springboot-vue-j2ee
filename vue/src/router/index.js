import { createRouter, createWebHistory } from 'vue-router'
import User from '../views/User.vue'
import Login from '../views/Login.vue'
import Layout from '../layout/Layout.vue'
import Register from "@/views/Register";
import Transport from "@/views/Transport";
import Person from "@/views/Person";
import Employee from "@/views/Employee";
import Receipt from "../views/Receipt";
import Shipment from "@/views/Shipment";
import Vehicle from "@/views/Vehicle";
import Deliver from "@/views/Deliver";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/person",
    children:[
      {
        path:'/user',
        name:'User',
        component:User
      },
      {
        path:'/person',
        name:'Person',
        component:Person
      },
      {
        path:'/transport',
        name:'Transport',
        component: Transport
      },
      {
        path:'/employee',
        name:'Employee',
        component:Employee
      },
      {
        path:'/receipt',
        name:'Receipt',
        component:Receipt
      },
      {
        path:'/shipment',
        name:'Shipment',
        component:Shipment
      },
      {
        path:'/vehicle',
        name:'Vehicle',
        component:Vehicle
      },
      {
        path:'/deliver',
        name:'Deliver',
        component:Deliver
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path:'/register',
    name:'Register',
    component: Register
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
