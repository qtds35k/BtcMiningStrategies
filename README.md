# Bitcoin Mining Strategies

We implement three strategies in Bitcoin mining that are based on miner's self-interest.
1. Majority Miner (forking attack)
	- This attack becomes feasible and more profitable when:
		- A party possesses the majority computing power of the network (say > 51%)
		- The party's mining on a fork that's not 6 blocks behind the longest chain
2. Selfish Miner (temporary block‐withholding attack)
	- When a party finds a block before the rest of the network, withholds the block. If other miners find a block while:
		- The party is already withholding 2+ secret blocks, it can choose to announce them and instanly causes the newly found block to be stale, increasing its effective share of mining rewards by wasting the hash power of the rest of the network.
		- The party is only withholding 1 secret block, it should immediately announce the block in the hope that it wins the broadcasting race to get its mining reward back.
3. Fee Sniping Miner
	- When a block is found containing unusually large transaction fee, the attacking party tries to fork the blockchain by:
		- Rejecting this block and mines (including the fat transaction) on top of the previous one.
		- If the party mines 2 blocks in a row, the fork becomes the longest chain and party gets to keep the fat transaction for itself.

Run **MiningSimulation.java** as JUnit test to start the simulation. Rewards of different strategies will be shown in the console.

## Reference
- [Majority Attack](https://en.bitcoin.it/wiki/Majority_attack)
- [Selfish Mining](https://www.investopedia.com/terms/s/selfish-mining.asp)
