<template>
    <div>
        <Sidebar></Sidebar>
        <h1>info</h1>
        <!-- <button @click="getPersons">get persons</button> -->
        
        <table style="margin:0px auto;">
            <tr>
                <th>name</th> 
                <th>tel</th>
                <th>email</th>
                <th>password</th>
                <th>identity</th>
            </tr>
            <tr v-for="person in persons" :key="person.id">
                <td>{{ person.name }}</td>
                <td>{{ person.tel }}</td>
                <td>{{ person.email }}</td>
                <td>{{ person.password }}</td>
                <td>{{ person.identity }}</td>                
            </tr>

        </table>




<!-- 
        <button @click="getUsername">find out which user has logged in</button>
        <h2>{{username}}</h2>
        <button @click="staff">staff</button> -->
    </div>
</template>

<script>
import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"info",
        created: function () {
            axios.get("/api/person/read/all")
            .then((response) => {
                if(response.data.message == "success")
                {
                    this.persons = response.data.data;
                }
            })
        },
        data(){
            return {
                persons:[],
            }
        },
        methods:{
            // getPersons(){
            //     axios.get("/api/person/read/all")
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.persons = response.data.data;
            //         }
            //     })
            // },
        },
        components:{
            Sidebar
        }
    }
</script>

<style lang="scss" scoped>
</style> 