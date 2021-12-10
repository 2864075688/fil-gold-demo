<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户code" prop="userCode">
        <el-input
          v-model="queryParams.userCode"
          placeholder="请输入用户code"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易hash" prop="transactionHash">
        <el-input
          v-model="queryParams.transactionHash"
          placeholder="请输入交易hash"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点类型" prop="nodeType">
        <el-select v-model="queryParams.nodeType" placeholder="请选择节点类型" clearable size="small">
          <el-option
            v-for="dict in nodeTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="applyNodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="节点id" align="center" prop="id" />-->
      <el-table-column label="用户code" align="center" prop="userCode" />
      <el-table-column label="交易hash" align="center" prop="transactionHash" />
      <el-table-column label="节点类型" align="center" prop="nodeType" :formatter="nodeTypeFormat" />
      <el-table-column label="金额" align="center" prop="amount" />
<!--      <el-table-column label="状态" align="center" prop="status" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listApplyNode, getApplyNode, delApplyNode, addApplyNode, updateApplyNode, exportApplyNode } from "@/api/fil/applyNode";

export default {
  name: "ApplyNode",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 节点申购订单表格数据
      applyNodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 节点类型0-大节点 1小节点字典
      nodeTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userCode: null,
        transactionHash: null,
        nodeType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("fil_node_type").then(response => {
      this.nodeTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询节点申购订单列表 */
    getList() {
      this.loading = true;
      listApplyNode(this.queryParams).then(response => {
        this.applyNodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 节点类型0-大节点 1小节点字典翻译
    nodeTypeFormat(row, column) {
      return this.selectDictLabel(this.nodeTypeOptions, row.nodeType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        nodeId: null,
        userCode: null,
        transactionHash: null,
        nodeType: null,
        amount: null,
        status: 0,
        createTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
  }
};
</script>
