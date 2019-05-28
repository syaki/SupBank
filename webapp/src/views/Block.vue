<template>
  <div class="block">
    <AppHeader/>
    <div class="main">
      <main role="main">
        <div class="main-inner">
          <div class="transactionCard card">
            <table class="listTable">
              <caption>Block detail:</caption>
              <tr>
                <td>BlockID:</td>
                <td>{{blockInfo.blockid}}</td>
              </tr>
              <tr>
                <td>Hash:</td>
                <td>{{blockInfo.hash}}</td>
              </tr>
              <tr>
                <td>Pre hash:</td>
                <td>{{blockInfo.prehash}}</td>
              </tr>
              <tr>
                <td>Height:</td>
                <td>{{blockInfo.height}}</td>
              </tr>
              <tr>
                <td>Transactions:</td>
              </tr>
              <tr v-for="t in txsList" :key="t.id" v-on:click="getTransactionDetail(t.transactionid)">
                <td>{{t.transactionid}}</td>
              </tr>
            </table>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "block",
  components: {
    AppHeader
  },
  data() {
    return {
      id: this.$route.query.q,
      blockInfo: {},
      txsList: []
    };
  },
  created() {
    var that = this;
    const id = this.$route.query.q;

    this.$axios({
      method: "post",
      url: "http://192.168.1.103:8990/block/getBlockById",
      data: {
        id: id
      }
    })
      .then(response => {
        const data = response.data;
        if (data.status.Ack === "success") {
          that.blockInfo = data.blockList[0];
          that.txsList = data.transactionList;
        } else {
          alert(data.status.ErrorMessage);
        }
      })
      .catch(error => {
        alert(error);
      });
  },
  methods: {
    getTransactionDetail: function(id) {
      this.$router.push({
        path: "transaction",
        query: {
          q: id
        }
      });
    }
  }
};
</script>

<style scoped>
.main-inner {
  position: relative;
  width: 1000px;
  padding: 0 16px;
  margin: 14px auto;
}

.searchCard .desc {
  margin-top: 18px;
  margin-bottom: 30px;
  font-size: 18px;
  color: #8590a6;
}

.listTable {
  display: flex;
  display: -webkit-box;
  display: -ms-flexbox;
  align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  justify-content: center;
  width: 100%;
}

.listTable caption {
  font-size: 24px;
  margin-bottom: 16px;
  color: #0084ff;
  text-align: left;
  width: 920px;
}

.listTable td {
  padding: 4px 8px;
}
</style>
