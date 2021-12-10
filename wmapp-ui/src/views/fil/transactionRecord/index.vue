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
      <el-form-item label="付款方" prop="payerFrom">
        <el-input
          v-model="queryParams.payerFrom"
          placeholder="请输入付款方"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到账方" prop="payerTo">
        <el-input
          v-model="queryParams.payerTo"
          placeholder="请输入到账方"
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
      <el-form-item label="金额状态" prop="amountType">
        <el-select v-model="queryParams.amountType" placeholder="请选择金额状态" clearable size="small">
          <el-option
            v-for="dict in amountTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="账单来源" prop="recordType">
        <el-select v-model="queryParams.recordType" placeholder="请选择账单来源" clearable size="small">
          <el-option
            v-for="dict in recordTypeOptions"
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

    <el-table v-loading="loading" :data="transactionRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="用户code" align="center" prop="userCode" />
      <el-table-column label="到账方" align="center" prop="payerTo" />
      <el-table-column label="交易hash" align="center" prop="transactionHash" />
      <el-table-column label="交易金额" align="center" prop="amount" />
      <el-table-column label="金额状态" align="center" prop="amountType" :formatter="amountTypeFormat" />
      <el-table-column label="账单来源" align="center" prop="recordType" :formatter="recordTypeFormat" />
      <el-table-column label="账单状态" align="center" prop="status" :formatter="statusFormat" />
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

    <!-- 添加或修改交易记录对话框 -->
  </div>
</template>

<script>
import { listTransactionRecord, getTransactionRecord} from "@/api/fil/transactionRecord";

export default {
  name: "TransactionRecord",
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
      // 交易记录表格数据
      transactionRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 金额状态字典
      amountTypeOptions: [],
      // 账单来源字典
      recordTypeOptions: [],
      // 账单状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userCode: null,
        payerFrom: null,
        payerTo: null,
        transactionHash: null,
        amountType: null,
        recordType: null,
        status: null,
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
    this.getDicts("fil_record_amount_type").then(response => {
      this.amountTypeOptions = response.data;
    });
    this.getDicts("fil_record_type").then(response => {
      this.recordTypeOptions = response.data;
    });
    this.getDicts("fil_record_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询交易记录列表 */
    getList() {
      this.loading = true;
      listTransactionRecord(this.queryParams).then(response => {
        this.transactionRecordList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 金额状态字典翻译
    amountTypeFormat(row, column) {
      return this.selectDictLabel(this.amountTypeOptions, row.amountType);
    },
    // 账单来源字典翻译
    recordTypeFormat(row, column) {
      return this.selectDictLabel(this.recordTypeOptions, row.recordType);
    },
    // 账单状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
        objId: null,
        userCode: null,
        payerFrom: null,
        payerTo: null,
        transactionHash: null,
        amount: null,
        amountType: null,
        recordType: null,
        status: null,
        createTime: null,
        updateTime: null
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
    }
  }
};
</script>
