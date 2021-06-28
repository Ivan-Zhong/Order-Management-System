<template>
    <div>
        <h1>logout</h1>
        <table>
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
        <button @click="logout">logout</button> -->
    </div>
</template>

<script>
import axios from "axios"
    export default {
        name:"logout",
        data(){
            return {
                persons:[]
            }
        },
        methods:{
            getUsername(){
                axios.get("/api/person/read/all")
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.persons = response.data.data;
                    }
                })
            },
    
        }

    }
</script>

<style lang="scss" scoped>

</style>