package edu.nyu.crypto.csci3033.miners;

import edu.nyu.crypto.csci3033.blockchain.Block;
import edu.nyu.crypto.csci3033.blockchain.NetworkStatistics;

/**
 * This class is actually written when I messed up writing SelfishMiner.
 * It turns our as long as ShMiner's mining power is at least equal to that of a SelfishMiner (if any),
 * ShMiner will have an absurdly dominating profit.
 * 
 * @author shw344, yyl346
 */

public class ShMiner extends BaseMiner implements Miner {
	private Block currentHead;
	private Block selfishHead;
	// private double myWeight;
	// private boolean attackOn = false;

	public ShMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
	}

	@Override
	public Block currentlyMiningAt() {
		return selfishHead;
	}

	@Override
	public Block currentHead() {
		return currentHead;
	}

	@Override
	public void blockMined(Block block, boolean isMinerMe) {
		if (isMinerMe) {
			// When I mine a new block, update my own head without broadcasting
			if (block.getHeight() > selfishHead.getHeight()) {
				this.selfishHead = block;
			}
		} else {
			if (selfishHead == null) {
				// When someone mines a block before I do, start mining from this block.
				selfishHead = block;
			} else if (block != null) {
				/*
				 * As long as I'm working on a chain,
				 * I don't give a crap if someone mines a new block.
				 * Whenever someone does, I just broadcast my own head, 
				 * which is only updated when a block is mined by myself.
				 */
				this.currentHead = selfishHead;
			}
		}
	}

	@Override
	public void initialize(Block genesis, NetworkStatistics networkStatistics) {
		this.currentHead = genesis;
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
		// myWeight = (double) this.getHashRate() / statistics.getTotalHashRate();
	}
}
