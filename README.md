
1.Kafka 的核心元件有哪些？  
Producer：  
負責將訊息發送到 Kafka 的指定 Topic。Producer 可以決定要將訊息送到哪個 Partition，通常可透過 key 或 round-robin 的方式進行分配。  
  
Consumer：  
從 Kafka 中訂閱並拉取訊息。Consumer 通常會屬於某個 Consumer Group，同一個 group 中的 Consumer 會協同處理 Topic 中不同 Partition 的訊息。  
  
Broker：  
Kafka 的伺服器實例，負責接收 Producer 發送的訊息、儲存訊息，以及提供給 Consumer 存取。每個 Broker 有自己的 ID，並負責儲存一部分的 Topic Partition。  
  
Topic：  
訊息的分類單位，Producer 將訊息發送到特定 Topic，Consumer 訂閱特定 Topic 以接收訊息。  
  
Partition：  
Topic 的分片單位，每個 Partition 是一個不可變的訊息序列，訊息在 Partition 中是有順序的。Kafka 為了擴展性與平行處理能力，允許每個 Topic 擁有多個 Partition。  
  
Cluster：  
一組 Kafka Broker 所組成的叢集。Cluster 提供容錯與擴展能力，透過 Zookeeper 或 KRaft 管理節點之間的協調與選舉。  
  
Retention（保留策略）：  
Kafka 對訊息的儲存是基於時間或容量限制的保留機制，例如可設定保留 7 天或最大 1GB。訊息過了保留期或超過儲存上限，就會被清除。  
  
Multiple Clusters（多叢集架構）：  
在大型系統中，可以使用多個 Kafka Cluster 來分散負載或達到地理冗餘（Geo-replication）。通常會搭配 MirrorMaker 等工具進行跨叢集的訊息複製。  
  
2.什麼是 Topic？什麼是 Partition？  
Topic：Kafka 的邏輯分類單位，像是資料的主題（如：logs、orders）。  
  
Partition：Topic 的實體分片。每個 Topic 可以有多個 Partition，每個 Partition 內的訊息是有序的。  
  
3.Kafka 如何確保訊息的順序性？  
Kafka 在單一 Partition 中能保證順序性，跨 Partition 則無法。  
如果你的應用場景需要訊息順序一致，務必確保使用同一個 key 並控制在單一 Partition 中處理  
  
如果需要全域順序，要保證所有訊息都進入同一個 Partition（但這樣會降低效能）。  
  
  
4.Kafka 如何實現高可用性與容錯？  
每個 Partition 可以設定多個副本（replicas）。  
  
一個是 leader，其餘是 follower。  
  
Leader 處理讀寫，follower 負責同步資料。  
  
若 leader 故障，Kafka（或 ZooKeeper/KRaft）會選出新的 leader 確保服務不中斷。  
  
5.Kafka 中的 Producer 是如何傳送資料的？  
Producer 將資料送到指定 Topic。  
  
Kafka 根據設定的 partitioner 決定要把資料寫入哪個 Partition。  
  
Producer 支援同步（sync）與非同步（async）傳送。  
  
  
  
6.Kafka 的 Consumer 有哪些消費模式？  
常見的「消費模式」  
分區（partition）與 Consumer Group   
  
7.什麼是 Consumer Group？其作用是什麼？  
把多個 Consumer 視為同一組來協調讀取 Topic 分區 (partition)  
組內的 Consumer 共同完成「只讓每條訊息在這個群組裡被處理一次」的任務  
  
8.Kafka 的 Offset 是如何管理的？  
offset（位移） 是「某個 partition 內訊息的順序編號」。  
Consumer 讀到哪，就把「最後處理完成的 offset」記下來，下次重啟或故障後能從該位置繼續拉取。  
  
9.Kafka 的訊息持久化是怎麼實現的？(要再聽一下老師的說法)  
“先寫磁碟，後記憶體”——把磁碟當 commit log，再用記憶體做快取，以簡潔機制換取確定性與高效能。  
  

  
  
10.Kafka 是如何處理訊息的重複（Exactly-once）問題的？(要再聽一下老師的說法)  
Kafka 的 Exactly‑once = Idempotent Producer + 事務 API + 讀已提交隔離級別  
只要 Producer、Broker、Consumer 三端都照協定運行，  
就能在單一 Cluster 內達到「不重複、不遺失、不中斷」的保證。  
  

11.Kafka 的訊息是推送還是拉取模式？為什麼這麼設計？  
Kafka 採用 拉取（Pull）模式：  
  
Consumer 主動向 Broker 拉資料。  
  
好處：簡化流控設計，Consumer 可依自身能力決定拉取速度。  
  
推送（Push）容易造成 Consumer 過載。  

12.Kafka 的 Rebalance 是什麼？會有什麼影響？  
Rebalance：Consumer Group 成員變動（加入/離開）時，Kafka 會重新分配 Partition。  
  
影響：  

會導致短暫無法消費（因為正在重新分配）。  
  
若頻繁發生，會影響穩定性與效能。  
  
  
  
13.Kafka 的 Log Retention 是什麼？  
Kafka 訊息並不是消費完就刪除，而是根據設定的：  
  
保留時間（retention.ms）  

檔案大小（retention.bytes）  
  
超過就會清除最舊的 log segment。   
  
以節省磁碟空間  
  
14.Kafka 中如何調優吞吐量與延遲？  
a.吞吐量（Throughput）調優  
目標是讓單位時間內傳輸更多資料。常見的調整方式有：  

批次大小（batch.size）  
  
調大 batch.size（Producer 端設定），可以讓 Producer 一次送更多資料，減少頻繁送小量資料的 overhead。  
  
壓縮（compression.type）  
  
使用 snappy 或 lz4 壓縮資料。壓縮後資料變小，可以傳更多筆，但注意 CPU 也會增加負擔。   
  
訊息累積等待時間（linger.ms）  
  
設定 linger.ms，讓 Producer 等待更多訊息湊成一批再送出，而不是馬上送一筆。比如設成 5-10 毫秒，吞吐量可以明顯上升。  
  
分區數量（Topic partitions）  
  
增加 Partition 數量。Kafka 的吞吐量基本上跟 partition 數量成正比，但也不能無限開，要看 Broker 效能。  
  
Broker I/O 調整  
  
調大 Broker 的 num.network.threads 和 num.io.threads，增加 Broker 同時處理網路和磁碟 I/O 的能力。  
  
批次寫入磁碟（log.flush.interval.messages / log.flush.interval.ms）  
  
Kafka 預設是非同步寫磁碟，為了效能不每次寫都 flush。如果要極致吞吐量，應該維持非同步，讓 OS 處理 flush。  
  
b.延遲（Latency）調優  
目標是降低一筆訊息從產生到消費的延遲。重點是減少等待與處理時間：  
  
減小批次大小與等待時間  
  
Producer 調小 batch.size，並降低 linger.ms（甚至設成 0），讓訊息即來即送。  
  
消費者提早拉取（fetch.min.bytes 和 fetch.max.wait.ms）  
  
Consumer 把 fetch.min.bytes 設小一點（例如 1KB），並讓 fetch.max.wait.ms 也低（比如 10ms），可以更快拿到訊息。  
  
Replication factor 與 ISR  
  
設低一些的 acks 值（像 acks=1），不用每次等所有副本都確認，可以快很多。但注意會犧牲可靠性。  
  
Broker/Producer 端網路與硬體優化  
  
避免磁碟瓶頸（用 SSD）、使用更快的網路（如 10Gbps），這些會大幅縮短延遲。  
  
合理的 GC（Garbage Collection）調整  
  
JVM GC 如果卡很久，會影響 Kafka 延遲，要優化 Producer、Broker、Consumer 的 JVM Heap Size 與 GC policy（如 G1GC）。  
  
15.Kafka 的 Acknowledgement（acks）有哪幾種模式？  
acks=0：不等待回應，最快，但可能資料丟失。  
  
acks=1：leader 收到就回應，較安全，仍可能資料遺失。  
  
acks=all 或 -1：所有 ISR（同步副本）都確認後才回應，最安全但延遲最高。  
  
