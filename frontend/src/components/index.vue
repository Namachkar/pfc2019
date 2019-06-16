<template>
    <!--Index-->
    <div id="index" class="container">
        <login></login>

        <div class="col-sm-3 my-1">
            <label class="sr-only" for="inlineFormInputGroupUsername">Username</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text"><i class="fa fa-user-circle"></i></div>
                </div>
                <input type="text" class="form-control" id="inlineFormInputGroupUsername" placeholder="Username">
            </div>
        </div>
        <div class="col-sm-3 my-1">
            <label class="sr-only" for="inlineFormInputGroupPassword">Password</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text"><i class="fa fa-lock"></i></div>
                </div>
                <input type="password" class="form-control" id="inlineFormInputGroupPassword"
                       placeholder="Password">
            </div>
        </div>
        <div class="col-auto my-1">
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </div>
    <!--Index-->
</template>
<script>
    import login from '@/components/Login'
    export default {
        name:'index',
        components:{
            login
        }
    };
</script>
<style>
</style>
