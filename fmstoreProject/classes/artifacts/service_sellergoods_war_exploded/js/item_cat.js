new Vue({
    el: "#app",
    data: {
        categoryList: [],//分类的数据
        page: 1,//当前页码
        pageSize: 10,//一页有多少条数据
        total: 100,//总记录数
        maxPage: 9,//最大分页数
        grade:1, /*当前级别*/
        gradeEntity1:{}, /*面包屑导航1*/
        gradeEntity2:{} /*面包屑导航2*/
    },
    methods: {
        /**
         * 查询父级id的方法
         * @param id
         */
        selectCateByParentId: function (id) {
            _this = this;
            axios.post("/itemCat/findByParentId.do?parentId=" + id)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.categoryList = response.data;
                    console.log(response);
                }).catch(function (reason) {
                console.log(reason);
            })
        },
        //分页条件查询
        pageHandler: function (page) {
            var _this = this;//获取data的this
            this.page = page;//获取page的页码
          /*  axios.post("/spec/search.do?page=" + this.page + "&pageSize=" + this.pageSize, this.searchSpec)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.specList = response.data.rows;//返回的数据
                    _this.total = response.data.total;//返回的总记录数
                }).catch(function (reason) {//失败返回
                console.log(reason);
            })*/
        },
        nextGrade: function (item) {
            if(this.grade==1){//如果级别等于1
                this.gradeEntity1={};
                this.gradeEntity2={};
        }
            if(this.grade==2){//如果级别等于2
                this.gradeEntity1=item;
                this.gradeEntity2={};
            }
            if(this.grade ==3){//如果级别等于3
                this.gradeEntity2=item;
            }
            this.selectCateByParentId(item.id);//查询父级id 等于子级id
        },
        //设置面包屑导航的级别
        setGrade:function(grade) {
            this.grade = grade;
        }
    },
    created: function () {
        this.selectCateByParentId(0);//页面加载 调用方法 查询父级id等于0的数据
    }
});